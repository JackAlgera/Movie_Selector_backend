package com.jack.applications.pojos;

import com.jack.applications.utils.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private static IdGenerator idGenerator = IdGenerator.getIdGenerator();

    private String roomId;
    private Map<String, User> connectedUsers;

    public Room() {
        this.roomId = idGenerator.getRandomId();
        this.connectedUsers = new HashMap<>();
    }

    public void addUser(User newUser) {
        this.connectedUsers.put(newUser.getUserId(), newUser);
    }

    public void removeUser(String userId) {
        this.connectedUsers.remove(userId);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Map<String, User> getConnectedUsers() {
        return connectedUsers;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", connectedUsers=" + connectedUsers +
                '}';
    }
}
