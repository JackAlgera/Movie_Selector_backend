package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.repos.SelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectionDAOImpl implements SelectionDAO {

    @Autowired
    private SelectionRepository selectionRepository;

    public List<Selection> getUserRatings(User user) {
        return selectionRepository.findSelectionsByUserIdAndRoomId(user.getUserId(), user.getRoomId());
    }

    @Override
    public List<Selection> getAllRatingsForRoom(String roomId) {
        return selectionRepository.findSelectionsByRoomId(roomId);
    }

    @Override
    public Selection createSelection(Selection selection) {
        return selectionRepository.save(selection);
    }

    @Override
    public void deleteAllUserRatings(User user) {
        selectionRepository.deleteAllInBatch(getUserRatings(user));
    }
}
