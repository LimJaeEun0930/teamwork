package CapstoneDesign.Backendserver.service;

import CapstoneDesign.Backendserver.domain.dto.ChatRoom;
import CapstoneDesign.Backendserver.domain.room.Room;
import CapstoneDesign.Backendserver.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
         chatRooms = new LinkedHashMap<>();
    }

    public List<Room> findAll() {
        return chatRoomRepository.findAllRoom();
    }

    public void createRoom(Room chatRoom) {
        chatRoomRepository.createRoom(chatRoom);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            log.info("Sent message!!");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
