package com.jack.applications.webservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectionId implements Serializable {
    private String roomId;
    private UUID userId;
    private Integer movieId;
}
