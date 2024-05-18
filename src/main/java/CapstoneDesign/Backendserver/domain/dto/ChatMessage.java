package CapstoneDesign.Backendserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private enum Status{ ENTER, MESSAGE, LEAVE}
    private String senderName;
    private String receivername;
    private String message;
    private String date;
    private Status status;
}
