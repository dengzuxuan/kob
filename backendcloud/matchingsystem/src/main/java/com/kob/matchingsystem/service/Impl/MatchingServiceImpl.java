package com.kob.matchingsystem.service.Impl;

import com.kob.matchingsystem.service.Impl.utils.MatchingPool;
import com.kob.matchingsystem.service.MatchingService;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool=  new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating,Integer botId) {
        System.out.println("add player:"+userId+" "+rating);
        matchingPool.addPlayer(userId,rating,botId);
        return "add player";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove player:"+userId);
        matchingPool.removePlayer(userId);
        return "remove player";
    }
}
