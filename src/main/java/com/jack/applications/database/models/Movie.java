package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @JsonProperty("id")
    private int movieId;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("adult")
    private boolean isAdult;
    private float budget;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "movie_genre",
//            joinColumns = @JoinColumn(name = "movieId"),
//            inverseJoinColumns = @JoinColumn(name = "genreId")
//    )
//    private List<Genre> genres;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private String homepage;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    private float popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    //@JsonProperty("production_companies")
    //private List<Company> productionCompanies;
    //@JsonProperty("production_countries")
    //private List<Country> productionCountries;
    @JsonProperty("release_date")
    private String releaseDate;
    private float revenue;
    private int runtime;
    //@JsonProperty("spoken_languages")
    //private List<Language> spokenLanguages;
    private String tagline;
    private String title;
    @JsonProperty("vote_average")
    private float averageVote;
    @JsonProperty("vote_count")
    private int voteCount;

    public Movie() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

//    public List<Company> getProductionCompanies() {
//        return productionCompanies;
//    }
//
//    public void setProductionCompanies(ArrayList<Company> productionCompanies) {
//        this.productionCompanies = productionCompanies;
//    }
//
//    public List<Country> getProductionCountries() {
//        return productionCountries;
//    }
//
//    public void setProductionCountries(ArrayList<Country> productionCountries) {
//        this.productionCountries = productionCountries;
//    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

//    public List<Language> getSpokenLanguages() {
//        return spokenLanguages;
//    }
//
//    public void setSpokenLanguages(ArrayList<Language> spokenLanguages) {
//        this.spokenLanguages = spokenLanguages;
//    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(float averageVote) {
        this.averageVote = averageVote;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

//    public List<Genre> getGenres() {
//        return genres;
//    }

//    public void setGenres(ArrayList<Genre> genres) {
//        this.genres = genres;
//    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", imdbId=" + imdbId +
                ", isAdult=" + isAdult +
                ", budget=" + budget +
//                ", genres=" + genres +
                ", backdropPath='" + backdropPath + '\'' +
                ", homepage='" + homepage + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
//                ", productionCompanies=" + productionCompanies +
//                ", productionCountries=" + productionCountries +
//                ", releaseDate=" + releaseDate +
//                ", revenue=" + revenue +
//                ", runtime=" + runtime +
//                ", spokenLanguages=" + spokenLanguages +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", averageVote=" + averageVote +
                ", voteCount=" + voteCount +
                '}';
    }
}
