package com.vang.on_spring_boot_websocket.controller;

import com.vang.on_spring_boot_websocket.dto.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestWebsocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public TestWebsocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO dto) {

        template.convertAndSend("/topic/message", dto);
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/sendMessage")
    public void receiveMessages(@RequestBody TextMessageDTO dto) {
        int a = 1;
    }

    @SendTo("/topic/message")
    public TextMessageDTO broadcast(@Payload TextMessageDTO dto) {
        return dto;
    }

}