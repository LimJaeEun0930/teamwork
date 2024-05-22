package CapstoneDesign.Backendserver.repository;

import CapstoneDesign.Backendserver.domain.dto.ChatRoom;
import CapstoneDesign.Backendserver.domain.room.Room;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatRoomRepository {

    @PersistenceContext
    EntityManager em;

//    private Map<String, ChatRoom> chatRoomMap;
//
//    @PostConstruct
//    public void init(){
//        chatRoomMap = new HashMap<>();
//    }

    // 전체 채팅방 조회
    public List<Room> findAllRoom(){

        return em.createQuery("select r from Room r", Room.class).getResultList();
    }
//
//    // roomId 기준으로 채팅방 찾기
//    public ChatRoom findByRoomId(String roomId){
//
//        return chatRoomMap.get(roomId);
//    }

    public void createRoom(Room chatRoom){
        em.persist(chatRoom);

    }

//    // 채팅방 인원 +1
//    public void increaseUser(String roomId){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.setUserCount(chatRoom.getUserCount()+1);
//    }

    // 채팅방 인원 -1
//    public void decreaseUser(String roomId){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.setUserCount(chatRoom.getUserCount()-1);
//    }
//
//    //채팅방 유저 리스트에 유저추가
//    public  String addUser(String roomId, String userName){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        String userUUID = UUID.randomUUID().toString();
//        //아이디 중복 확인 후 userList에 추가
//        chatRoom.getUserList().put(userUUID,userName);
//
//        return userUUID;
//    }
//
//    // 채팅방 유저 이름 중복 확인
//    public String isDuplicateName(String roomId,String username){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        String temp = username;
//
//        // 만약 username이 중복이라면 랜덤한 숫자를 붙여준다.
//        // 이 때 랜덤한 숫자를 붙였을때 getUserList 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기
//        while(chatRoom.getUserList().containsValue(temp)){
//            int ranNum = (int) (Math.random() * 100) + 1;
//            temp = username+ranNum;
//        }
//
//        return temp;
//    }
//
//    // 채팅방 유저 리스트 삭제
//    public void deleteUser(String roomId,String userUUID){
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.getUserList().remove(userUUID);
//    }
//
//    // 채팅방 userName 조회
//    public String getUserName(String roomId,String userUUID){
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//
//        return chatRoom.getUserList().get(userUUID);
//    }
//
//    //채팅방 전체 userList 조회
//    public List<String> getUserList(String roomId){
//        List<String> list = new ArrayList<>();
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//
//        chatRoom.getUserList().forEach((key,value) -> list.add(value));
//
//        return list;
//    }


}
