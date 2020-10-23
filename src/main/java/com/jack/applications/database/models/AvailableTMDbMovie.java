package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableTMDbMovie extends BaseModel {

    @JsonProperty("adult")
    private boolean isAdult;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("popularity")
    private float popularity;
    @JsonProperty("video")
    private boolean isVideo;

    public AvailableTMDbMovie() {
        super();
    }

    public AvailableTMDbMovie(boolean isAdult, int id, String originalTitle, float popularity, boolean isVideo) {
        super(id);
        this.isAdult = isAdult;
        this.id = id;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.isVideo = isVideo;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    @Override
    public String toString() {
        return "AvailableTMDbMovie{" +
                "isAdult=" + isAdult +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", popularity=" + popularity +
                ", isVideo=" + isVideo +
                '}';
    }
}
