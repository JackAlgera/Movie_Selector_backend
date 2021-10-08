package com.jack.applications.webservice.handlers;

import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.exceptions.UserNotFoundException;
import com.jack.applications.webservice.models.User;
import com.jack.applications.webservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserHandler {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SelectionHandler selectionHandler;

    public User createUser(User user) {
        user.setUserId(idGenerator.getRandomUUID());
        return userRepo.saveAndFlush(user);
    }

    public void deleteUser(UUID userId) {
        User user = findUserById(userId);
        selectionHandler.deleteAllUserRatings(user);
        userRepo.deleteById(userId);
    }

    public User findUserById(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) {
        if (userRepo.existsById(user.getUserId())) {
            return userRepo.save(user);
        } else {
            throw new UserNotFoundException(user.getUserId());
        }
    }
}
