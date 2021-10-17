package com.jack.applications.webservice.models;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class SelectionId implements Serializable {
    private String roomId;
    private UUID userId;
    private Integer movieId;
}
