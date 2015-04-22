package ru.saransklife.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SEANCE.
 */
public class Seance {

    private Long id;
    private java.util.Date datetime;
    private String hallName;
    private String type;
    private Integer price;
    private Long placeId;
    private long event_id;

    public Seance() {
    }

    public Seance(Long id) {
        this.id = id;
    }

    public Seance(Long id, java.util.Date datetime, String hallName, String type, Integer price, Long placeId, long event_id) {
        this.id = id;
        this.datetime = datetime;
        this.hallName = hallName;
        this.type = type;
        this.price = price;
        this.placeId = placeId;
        this.event_id = event_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDatetime() {
        return datetime;
    }

    public void setDatetime(java.util.Date datetime) {
        this.datetime = datetime;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

}