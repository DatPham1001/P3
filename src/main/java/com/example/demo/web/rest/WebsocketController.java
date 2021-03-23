package com.example.demo.web.rest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:3000")
@Controller

public class WebsocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/user")
    public String sendRoomStats(){
        return "test";
    }
}
