package com.example.demo.web.rest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @MessageMapping
    @SendTo("/topic/user")
    public String sendRoomStats(){
        return "test";
    }
}
