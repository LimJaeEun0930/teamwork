package CapstoneDesign.Backendserver.domain.room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Room {
    private final String roomName;
    private final Category category;
    private String roomDescription;

    public Room(String roomName, Category category, String roomDescription) {
        this.roomName = roomName;
        this.category = category;
        this.roomDescription = roomDescription;
    }
}
