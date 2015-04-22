package ru.saransklife.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EVENT_PARAMS.
 */
public class EventParams {

    private Long id;
    private String english_name;
    private String description;
    private String country;
    private String year;
    private String genre;
    private String duration;
    private String start_age;
    private String director;
    private String actors;
    private String afisha;
    private String json_images;

    public EventParams() {
    }

    public EventParams(Long id) {
        this.id = id;
    }

    public EventParams(Long id, String english_name, String description, String country, String year, String genre, String duration, String start_age, String director, String actors, String afisha, String json_images) {
        this.id = id;
        this.english_name = english_name;
        this.description = description;
        this.country = country;
        this.year = year;
        this.genre = genre;
        this.duration = duration;
        this.start_age = start_age;
        this.director = director;
        this.actors = actors;
        this.afisha = afisha;
        this.json_images = json_images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart_age() {
        return start_age;
    }

    public void setStart_age(String start_age) {
        this.start_age = start_age;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getAfisha() {
        return afisha;
    }

    public void setAfisha(String afisha) {
        this.afisha = afisha;
    }

    public String getJson_images() {
        return json_images;
    }

    public void setJson_images(String json_images) {
        this.json_images = json_images;
    }

}