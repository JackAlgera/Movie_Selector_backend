package com.jack.applications.webservice.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.daos.RoomDAO;
import com.jack.applications.webservice.exceptions.MovieAlreadyFoundForRoomException;
import com.jack.applications.webservice.exceptions.RoomNotFoundException;
import com.jack.applications.webservice.models.Room;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @JsonIgnore
    private final static Long MAX_TIME_BEFORE_CLOSING_ROOM = 600_000L; // 600s = 10 mins

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private SelectionService selectionService;

    @PostConstruct
    private void initialise() {
//        RoomGarbageCollector garbageCollector = new RoomGarbageCollector(this);
//        garbageCollector.start();
    }

    public Room generateNewRoom() {
        Room newRoom = new Room(idGenerator.getRandomId());
        return roomDAO.createRoom(newRoom);
    }

    public Room findRoomById(String roomId) {
        return roomDAO.findRoomById(roomId)
               .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    public List<Room> findAllRooms() {
        return roomDAO.findAllRooms();
    }

    public void deleteRoom(String roomId) {
        Room room = findRoomById(roomId);
        for (User user : userService.findUsersByRoomId(room.getRoomId())) {
            user.setRoomId(null);
            userService.updateUser(user);
        }
        roomDAO.deleteRoom(room.getRoomId());
    }

    public void checkIfRoomFoundMovie(String roomId) {
        Room room = findRoomById(roomId);

        if (room.getFoundMovieId() != null) {
            throw new MovieAlreadyFoundForRoomException(room.getRoomId(), room.getFoundMovieId());
        }

        List<User> connectedUsers = userService.findUsersByRoomId(room.getRoomId());

        int nbrOfConnectedUsers = connectedUsers.size();

        if (nbrOfConnectedUsers <= 1) {
            return;
        }

        Map<Integer, List<Selection>> ratingsPerMovie = selectionService.getAllRatingsForRoom(room.getRoomId())
                                                .stream().collect(Collectors.groupingBy(Selection::getMovieId));

        for (Map.Entry<Integer, List<Selection>> entry : ratingsPerMovie.entrySet()) {
            if (entry.getValue().size() < nbrOfConnectedUsers) {
                continue;
            }

            float avgRating = entry.getValue().stream().mapToInt(Selection::getRating).sum() / (float) nbrOfConnectedUsers;
            if (avgRating >= 4.1) {
                room.setFoundMovieId(entry.getKey());
                roomDAO.updateRoom(room);
                break;
            }
        }

        room.setLastModified(Instant.now());
        roomDAO.updateRoom(room);
    }

    public Room updateRoom(Room room) {
        findRoomById(room.getRoomId());
        return roomDAO.updateRoom(room);
    }

    public void removeUnusedRooms() {
//        for (Room room : findAllRooms()) {
//            if (room.shouldCloseRoom()) {
//                System.out.printf("Removing room %s\n", room.getRoomId());
//                roomRepo.deleteById(room.getRoomId());
//            }
//        }
    }
}
