package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    //userid:websocket实例 static是对所有实例都可见【相当于全局变量】
    public static ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();
    //匹配池
    private static final CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<>();
    private User user;
    private static UserMapper userMapper;

    private Game game = null;

    public static RecordMapper recordMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    private Session session = null;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        System.out.println("connected");
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(this.user!=null){
            users.put(userId,this);
        }else{
            this.session.close();
        }
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected");
        if(this.user!=null){
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }
    }

    private void startMatching(){
        System.out.println("startMatching");
        matchpool.add(this.user);

        while (matchpool.size()>=2){
            Iterator<User> it = matchpool.iterator();
            User a = it.next(),b=it.next();
            matchpool.remove(a);
            matchpool.remove(b);
            Game game = new Game(13,14,20,a.getId(),b.getId());
            game.createMap();

            users.get(a.getId()).game = game;
            users.get(b.getId()).game = game;

            game.start();

            JSONObject respGame = new JSONObject();
            respGame.put("a_id",game.getPlayerA().getId());
            respGame.put("a_sx",game.getPlayerA().getSx());
            respGame.put("a_sy",game.getPlayerA().getSy());
            respGame.put("b_id",game.getPlayerB().getId());
            respGame.put("b_sx",game.getPlayerB().getSx());
            respGame.put("b_sy",game.getPlayerB().getSy());
            respGame.put("gamemap",game.getG());

            //匹配信息传给A
            JSONObject respA = new JSONObject();
            respA.put("game",respGame);
            respA.put("event","start-matching");
            respA.put("opponent_username",b.getUsername());
            respA.put("opponent_photo",b.getPhoto());

            users.get(a.getId()).sendMessage(respA.toJSONString());

            //匹配信息传给B
            JSONObject respB = new JSONObject();
            respB.put("game",respGame);
            respB.put("event","start-matching");
            respB.put("opponent_username",a.getUsername());
            respB.put("opponent_photo",a.getPhoto());

            users.get(b.getId()).sendMessage(respB.toJSONString());

        }
    }
    private void stoptMatching(){
        System.out.println("stoptMatching");
        matchpool.remove(this.user);
    }
    private void move(int direction) {
        if (game.getPlayerA().getId().equals(user.getId())) {
            game.setNextStepA(direction);
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            game.setNextStepB(direction);
        }
    }


    @OnMessage
    public void onMessage(String message, Session session) { //当做路由
        // 从Client接收消息
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if(("start-matching").equals(event)){
            startMatching();
        }else if(("stop-matching").equals(event)){
            stoptMatching();
        }else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //后端向前端发送信息
    public void sendMessage(String message){
        synchronized (this.session){
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

