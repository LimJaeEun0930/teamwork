package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.dto.ChatRoom;
import CapstoneDesign.Backendserver.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
@Slf4j
public class ChatRoomController {

    private final ChatRoomRepository repository;

    @GetMapping
    public String chatRoomList(Model model) {
        model.addAttribute("list", repository.findAllRoom());
        log.info("Show All ChatList : {}", repository.findAllRoom());
        return "chat";
    }

    @PostMapping("/create")
    public String createRoom(@RequestParam String roomName, RedirectAttributes rttr) {
        ChatRoom chatRoom = repository.createChatRoom(roomName);
        log.info("Create ChatRoom : {}", chatRoom);
        rttr.addFlashAttribute("roomName", chatRoom);
        return "redirect:/chat";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> getRooms() {
        return repository.findAllRoom();
    }

    @GetMapping("/joinroom")
    public String joinRoom(@RequestParam String roomId, Model model) {
        log.info("roomId : {}", roomId);
        model.addAttribute("room", repository.findByRoomId(roomId));
        return "chatroom";
    }
}
//    private final ChatRoomRepository chatRoomRepository;
//
//    //chatting list
//    @GetMapping
//    public String rooms(Model model)
//    {
//        return "/chat";
//    }
//
//    //All chattingRoom list
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room()
//    {
//        return chatRoomRepository.findAllRoom();
//    }
//
//    //create chattingRoom
//    @PostMapping("/create")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam String name)
//    {
//        return chatRoomRepository.createChatRoom(name);
//    }
//
//    //chattingRoom Entry
//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId)
//    {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }
//
//    //search specific chattingRoom
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId)
//    {
//        return chatRoomRepository.findRoomById(roomId);
//    }

