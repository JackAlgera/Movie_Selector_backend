package com.jack.applications.webservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SelectionId.class)
public class Selection implements Serializable {
    @Id
    @Column(updatable = false, nullable = false, length = 50)
    private String roomId;
    @Id
    @Column(updatable = false, nullable = false, length = 50)
    @Type(type = "uuid-char")
    private UUID userId;
    @Id
    private Integer movieId;
    private Integer rating;
}
