package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId,Integer aBotid, Integer bId,Integer bBotid) {
        System.out.println("start game: "+aId +" "+bId);
        WebSocketServer.startGame(aId,aBotid,bId,bBotid);
        return "start game ok";
    }
}
