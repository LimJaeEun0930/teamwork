package CapstoneDesign.Backendserver.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private MessageType type; //메시지 타입
    private String roomId;// 방 번호
    private String sender;//채팅을 보낸 사람
    private String message;// 메세지
    private String time; // 채팅 발송 시간

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }


}
