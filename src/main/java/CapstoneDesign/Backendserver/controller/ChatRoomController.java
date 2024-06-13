package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.JobCategory;
import CapstoneDesign.Backendserver.domain.room.Room;
import CapstoneDesign.Backendserver.repository.ChatRoomRepository;
import CapstoneDesign.Backendserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
@Slf4j
public class ChatRoomController {

    private final ChatRoomRepository repository;
    private final ChatService chatService;

    @ModelAttribute("jobCategory")
    public JobCategory[] JobCategories() {
        return JobCategory.values();
    }

    @GetMapping
    public String chatRoomList(Model model) {
        model.addAttribute("list", chatService.findAll());
        log.info("Show All ChatList : {}", chatService.findAll());
        return "chatting/chat";
    }
    @GetMapping("/create")
    public String createRoom_GET() {

        return "chatting/createChatRoom";
    }
    @PostMapping("/create")
    public String createRoom(@RequestParam String roomName, @RequestParam JobCategory jobCategory, @RequestParam String description) {
        Room new_room = new Room(roomName, jobCategory, description);
        chatService.createRoom(new_room);

        return "redirect:/chat";
    }

//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> getRooms() {
//        return repository.findAllRoom();
//    }

//    @GetMapping("/joinroom")
//    public String joinRoom(@RequestParam String roomId, Model model) {
//        log.info("roomId : {}", roomId);
//        model.addAttribute("room", repository.findByRoomId(roomId));
//        return "chatroom";
//    }
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

