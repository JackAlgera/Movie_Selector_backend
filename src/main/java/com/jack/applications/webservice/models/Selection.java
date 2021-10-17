package com.jack.applications.webservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SelectionId.class)
public class Selection implements Serializable {
    @Id
    private String roomId;
    @Id
    private UUID userId;
    @Id
    private Integer movieId;
    private Integer rating;
    private Instant createdOn = Instant.now();

    public Selection(String roomId, UUID userId, Integer movieId, Integer rating) {
        this.roomId = roomId;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.createdOn = Instant.now();
    }
}
