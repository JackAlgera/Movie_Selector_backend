package com.jack.applications.webservice.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDCharType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User implements Serializable {
    @Id
    @Column(updatable = false, nullable = false, length = 50)
    @Type(type = "uuid-char")
    private UUID userId;
    private String userName;
    @ManyToOne
    @JsonBackReference
    private Room room;

    public String getRoomId() {
        return room == null ? null : room.getRoomId();
    }
}
