package com.jack.applications.webservice.handlers;

import com.jack.applications.webservice.models.Movie;
import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.SelectionId;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.repos.SelectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectionHandler {

    @Autowired
    private SelectionRepo selectionRepo;

    @Autowired
    private RoomHandler roomHandler;

    public void rateMovie(User user, Integer movieId, Integer rating) {
        selectionRepo.save(new Selection(user.getRoomId(), user.getUserId(), movieId, rating));
        roomHandler.checkIfRoomFoundMovie(user.getRoom());
    }

    public List<Selection> getUserRatings(User user) {
        return selectionRepo.findSelectionsByUserIdAndRoomId(user.getUserId(), user.getRoomId());
    }

    public boolean isCorrectRating(Integer rating) {
        return rating >= 1 && rating <= 5;
    }

    public void deleteAllUserRatings(User user) {
        selectionRepo.deleteInBatch(getUserRatings(user));
    }
}
