package ru.saransklife.place_entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PLACE_ENTITY.
 */
public class PlaceEntity {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private Float latitude;
    private Float longitude;
    private String description;
    private String photo_author;
    private String photo_path;
    private String information;
    private String working_time;
    private Float rating;
    private Integer view_count;
    private Integer recommended_count;

    public PlaceEntity() {
    }

    public PlaceEntity(Long id) {
        this.id = id;
    }

    public PlaceEntity(Long id, String name, String address, String phone, String email, String website, Float latitude, Float longitude, String description, String photo_author, String photo_path, String information, String working_time, Float rating, Integer view_count, Integer recommended_count) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photo_author = photo_author;
        this.photo_path = photo_path;
        this.information = information;
        this.working_time = working_time;
        this.rating = rating;
        this.view_count = view_count;
        this.recommended_count = recommended_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_author() {
        return photo_author;
    }

    public void setPhoto_author(String photo_author) {
        this.photo_author = photo_author;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getWorking_time() {
        return working_time;
    }

    public void setWorking_time(String working_time) {
        this.working_time = working_time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Integer getRecommended_count() {
        return recommended_count;
    }

    public void setRecommended_count(Integer recommended_count) {
        this.recommended_count = recommended_count;
    }

}