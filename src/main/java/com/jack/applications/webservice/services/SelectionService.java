package com.jack.applications.webservice.services;

import com.jack.applications.webservice.daos.SelectionDAO;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SelectionService {

    @Autowired
    private SelectionDAO selectionDAO;

    @Autowired
    private RoomService roomService;

    public List<Selection> getUserRatings(User user) {
        return selectionDAO.getUserRatings(user);
    }

    public List<Selection> getAllRatingsForRoom(String roomId) {
        return selectionDAO.getAllRatingsForRoom(roomId);
    }

    public void deleteAllUserRatings(User user) {
        selectionDAO.deleteAllUserRatings(user);
    }

    public void rateMovie(User user, Integer movieId, Integer rating) {
        user.setLastModified(Instant.now());
        selectionDAO.createSelection(new Selection(user.getRoomId(), user.getUserId(), movieId, rating));
        roomService.checkIfRoomFoundMovie(user.getRoomId());
    }

    public boolean isCorrectRating(Integer rating) {
        return rating >= 1 && rating <= 5;
    }

}
