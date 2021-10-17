package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.repos.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomDAOImpl implements RoomDAO {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }
}
