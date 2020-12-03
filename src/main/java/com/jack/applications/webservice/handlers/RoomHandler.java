package com.jack.applications.webservice.handlers;

import com.jack.applications.webservice.models.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoomHandler {

    private Map<String, Room> availableRooms;

    public RoomHandler() {
        this.availableRooms = new HashMap<>();
    }

    public Room getRoom(String roomId) {
        return this.availableRooms.get(roomId);
    }

    public Room createNewRoom() {
        Room newRoom = new Room();
        this.availableRooms.put(newRoom.getRoomId(), newRoom);
        return newRoom;
    }

    public Room removeRoom(String roomId) {
        return this.availableRooms.remove(roomId);
    }

    public Map<String, Room> getAvailableRooms() {
        return availableRooms;
    }

    public void cleanRooms() {
        ArrayList<Room> currentRooms = new ArrayList<>(availableRooms.values());
        for (Room room : currentRooms) {
            if (room.getConnectedUsers().size() <= 0) {
                availableRooms.remove(room.getRoomId());
            }
        }
    }
}
