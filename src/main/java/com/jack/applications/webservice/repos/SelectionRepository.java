package com.jack.applications.webservice.repos;

import com.jack.applications.webservice.models.Selection;
import com.jack.applications.webservice.models.SelectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, SelectionId> {

    List<Selection> findSelectionsByUserIdAndRoomId(UUID userId, String roomId);
    List<Selection> findSelectionsByRoomId(String roomId);
}
