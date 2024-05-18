package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.dto.ChatRoom;
import CapstoneDesign.Backendserver.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    //chatting list
    @GetMapping("/room")
    public String rooms(Model model)
    {
        return "/chat/room";
    }

    //All chattingRoom list
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room()
    {
        return chatRoomRepository.findAllRoom();
    }

    //create chattingRoom
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name)
    {
        return chatRoomRepository.createChatRoom(name);
    }

    //chattingRoom Entry
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId)
    {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    //search specific chattingRoom
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId)
    {
        return chatRoomRepository.findRoomById(roomId);
    }
}
