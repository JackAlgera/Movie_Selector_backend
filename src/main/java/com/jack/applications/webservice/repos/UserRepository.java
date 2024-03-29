package com.jack.applications.webservice.repos;

import com.jack.applications.webservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findUsersByRoomId(String roomId);
}
