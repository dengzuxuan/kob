package com.kob.matchingsystem.service.Impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();

    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";
    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId,Integer rating,Integer botId){
        lock.lock();
        try {
            players.add(new Player(userId,rating,botId,0));
        }finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for(Player player:players){
                if(!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players=newPlayers;
        }finally {
            lock.unlock();
        }
    }

    private void increasingWaitingTime(){
        for (Player player:players){
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

    private void matchPlayer(){//尝试匹配
        boolean[] userMatched =new  boolean[players.size()];
        for(int i=0;i<players.size();i++){
            if(userMatched[i]) continue;
            for(int j = i+1;j<players.size();j++){
                if(userMatched[j]) continue;
                Player a = players.get(i),b=players.get(j);
                if(checkMatched(a,b)){
                    userMatched[i] = userMatched[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for(int i=0;i<players.size();i++){
            if(!userMatched[i]){
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    private boolean checkMatched(Player a,Player b){//判断两名玩家是否匹配
        int ratingDelta = Math.abs(a.getRating()-b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(),b.getWaitingTime());
        return ratingDelta <= waitingTime*10;
    }

    private void sendResult(Player a,Player b){//返回匹配结果
        System.out.println("send result"+a+b);
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("a_id",a.getUserId().toString());
        data.add("a_bot_id",a.getBotId().toString());
        data.add("b_id",b.getUserId().toString());
        data.add("b_bot_id",b.getBotId().toString());
        restTemplate.postForObject(startGameUrl,data,String.class);
    }
    @Override
    public void run(){
        //死循环 每次sleep一秒
        while (true){
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increasingWaitingTime();
                    matchPlayer();
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
