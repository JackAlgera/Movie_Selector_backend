package com.jack.applications.webservice.handlers;

import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomHandler {

    @Autowired
    private IdGenerator idGenerator;

    private final Map<String, Room> availableRooms;

    public RoomHandler() {
        this.availableRooms = new HashMap<>();

        RoomGarbageCollector garbageCollector = new RoomGarbageCollector(this);
        garbageCollector.start();
    }

    public Room getRoom(String roomId) {
        return this.availableRooms.get(roomId);
    }

    public Room createNewRoom() {
        Room newRoom = new Room(idGenerator.getRandomId());
        this.availableRooms.put(newRoom.getRoomId(), newRoom);
        return newRoom;
    }

    public Room removeRoom(String roomId) {
        return this.availableRooms.remove(roomId);
    }

    public List<Room> getAvailableRooms() {
        return new ArrayList<>(availableRooms.values());
    }

    public void cleanRooms() {
        ArrayList<Room> currentRooms = new ArrayList<>(availableRooms.values());
        for (Room room : currentRooms) {
            if (room.shouldCloseRoom()) {
                System.out.printf("Removing room %s\n", room.getRoomId());
                availableRooms.remove(room.getRoomId());
            }
        }
    }

    public boolean isCorrectRating(Integer likeRating) {
        return likeRating <= 5 && likeRating >= 1;
    }
}
