package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.models.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDAO {

    Room createRoom(Room room);
    void deleteRoom(String roomId);
    Optional<Room> findRoomById(String roomId);
    List<Room> findAllRooms();
    Room updateRoom(Room room);
}
