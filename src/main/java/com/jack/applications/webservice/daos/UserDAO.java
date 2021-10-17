package com.jack.applications.webservice.daos;

import com.jack.applications.webservice.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    User createUser(User user);
    void deleteUser(UUID userId);
    Optional<User> findUserById(UUID userId);
    List<User> findAllUsers();
    User updateUser(User user);
    List<User> findUsersByRoomId(String roomId);
}
