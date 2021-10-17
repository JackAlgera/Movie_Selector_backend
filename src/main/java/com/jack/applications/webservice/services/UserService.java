package com.jack.applications.webservice.services;

import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.daos.UserDAO;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private SelectionService selectionService;

    public User createUser(User user) {
        user.setUserId(idGenerator.getRandomUUID());
        return userDAO.createUser(user);
    }

    public void deleteUser(UUID userId) {
        User user = findUserById(userId);
        selectionService.deleteAllUserRatings(user);
        userDAO.deleteUser(userId);
    }

    public User findUserById(UUID userId) {
        return userDAO.findUserById(userId)
               .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    public User updateUser(User user) {
        findUserById(user.getUserId());
        user.setLastModified(Instant.now());
        return userDAO.updateUser(user);
    }

    public List<User> findUsersByRoomId(String roomId) {
        return userDAO.findUsersByRoomId(roomId);
    }
}
