package com.jack.applications.webservice.repos;

import com.jack.applications.webservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, String> {
}
