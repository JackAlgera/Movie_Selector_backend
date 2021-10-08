package com.jack.applications.webservice.handlers;

import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.exceptions.MovieAlreadyFoundForRoomException;
import com.jack.applications.webservice.exceptions.RoomNotFoundException;
import com.jack.applications.webservice.models.Movie;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.repos.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoomHandler {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private MovieHandler movieHandler;

    @PostConstruct
    private void initialise() {
//        RoomGarbageCollector garbageCollector = new RoomGarbageCollector(this);
//        garbageCollector.start();
    }

    public Room findRoomById(String roomId) {
        return roomRepo.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    public Room generateNewRoom() {
        Room newRoom = new Room(idGenerator.getRandomId());
        return roomRepo.save(newRoom);
    }

    public void deleteRoom(String roomId) {
        roomRepo.deleteById(roomId);
    }

    public List<Room> findAllRooms() {
        return roomRepo.findAll();
    }

    public void removeUnusedRooms() {
        for (Room room : findAllRooms()) {
            if (room.shouldCloseRoom()) {
                System.out.printf("Removing room %s\n", room.getRoomId());
                roomRepo.deleteById(room.getRoomId());
            }
        }
    }

    public Integer getFoundMovieForRoom(String roomId) {
        Room room = findRoomById(roomId);
        return room.getFoundMovieId();
    }

    public void checkIfRoomFoundMovie(Room room) {
        if (room.getFoundMovieId() != null) {
            throw new MovieAlreadyFoundForRoomException(room.getRoomId(), room.getFoundMovieId());
        }

        int nbrOfConnectedUsers = room.getConnectedUsers().size();

        if (nbrOfConnectedUsers <= 1) {
            return;
        }

        Map<Integer, List<Selection>> ratingsPerMovie = room.getUserRatings()
                .stream().collect(Collectors.groupingBy(Selection::getMovieId));

        for (Map.Entry<Integer, List<Selection>> entry : ratingsPerMovie.entrySet()) {
            if (entry.getValue().size() < nbrOfConnectedUsers) {
                return;
            }

            float avgRating = entry.getValue().stream().mapToInt(Selection::getRating).sum() / (float) nbrOfConnectedUsers;
            System.out.println("Average rating : " + avgRating);
            if (avgRating >= 4.0) {
                room.setFoundMovieId(entry.getKey());
                roomRepo.saveAndFlush(room);
                break;
            }
        }
    }
}
