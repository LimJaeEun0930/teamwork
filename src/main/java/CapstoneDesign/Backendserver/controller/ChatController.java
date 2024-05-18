package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.dto.ChatMessage;
import CapstoneDesign.Backendserver.domain.dto.ChatRoom;
import CapstoneDesign.Backendserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;//유저 개개인에게 메시지를 전송하기위해 동적으로 토픽을 만들어야하는데,
                                                        // 하드코딩하지 않기위해 필요한것.

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessage receiveMessage(@Payload ChatMessage message)
    {
        return message;
    }
        @MessageMapping("/private-message")
        public ChatMessage recMessage(@Payload ChatMessage message)
        {
            simpMessagingTemplate.convertAndSendToUser(message.getReceivername(), "/private", message);
            return message;
        }

}
