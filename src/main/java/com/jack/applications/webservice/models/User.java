package com.jack.applications.webservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID userId;
    private String userName;
    private String roomId;
    private Instant createdOn;
    private Instant lastModified;

    public User() {
        Instant now = Instant.now();
        this.createdOn = now;
        this.lastModified = now;
    }
}
