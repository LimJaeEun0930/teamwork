package CapstoneDesign.Backendserver.domain.room;

import CapstoneDesign.Backendserver.domain.JobCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RoomId;
    private String roomName;
    private JobCategory category;
    private String roomDescription;

    public Room(String roomName, JobCategory category, String roomDescription) {
        this.roomName = roomName;
        this.category = category;
        this.roomDescription = roomDescription;
    }
}
