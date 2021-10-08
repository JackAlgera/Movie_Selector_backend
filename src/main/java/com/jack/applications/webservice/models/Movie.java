package com.jack.applications.webservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable {
    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;
    private String title;
    @Type(type = "text")
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    @ManyToMany
    private List<Genre> genres;
    @JsonProperty("poster_path")
    private String posterPath;
}
