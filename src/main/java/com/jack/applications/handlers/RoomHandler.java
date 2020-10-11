package com.jack.applications.handlers;

import com.jack.applications.pojos.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomHandler {

    private static RoomHandler instance;

    private Map<String, Room> availableRooms;

    private RoomHandler() {
        this.availableRooms = new HashMap<>();
    }

    public static RoomHandler getInstance() {
        if(instance == null) {
            instance = new RoomHandler();
        }

        return instance;
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
}
