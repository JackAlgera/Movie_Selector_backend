package com.jack.applications.webservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {
    @Id
    private String roomId;
    private Integer foundMovieId;
    private Instant createdOn;
    private Instant lastModified;

    public Room(String roomId) {
        this.roomId = roomId;
        this.foundMovieId = null;

        Instant now = Instant.now();
        this.createdOn = now;
        this.lastModified = now;
    }
}
