package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.User;

import java.util.List;

public interface SelectionDAO {

    List<Selection> getUserRatings(User user);
    List<Selection> getAllRatingsForRoom(String roomId);
    Selection createSelection(Selection selection);
    void deleteAllUserRatings(User user);
}
