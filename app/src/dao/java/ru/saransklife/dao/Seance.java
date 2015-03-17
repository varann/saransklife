package ru.saransklife.dao;

import ru.saransklife.dao.DaoSession;
import de.greenrobot.dao.DaoException;

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

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient SeanceDao myDao;

    private PlaceEntity placeEntity;
    private Long placeEntity__resolvedKey;


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

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSeanceDao() : null;
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

    /** To-one relationship, resolved on first access. */
    public PlaceEntity getPlaceEntity() {
        Long __key = this.placeId;
        if (placeEntity__resolvedKey == null || !placeEntity__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlaceEntityDao targetDao = daoSession.getPlaceEntityDao();
            PlaceEntity placeEntityNew = targetDao.load(__key);
            synchronized (this) {
                placeEntity = placeEntityNew;
            	placeEntity__resolvedKey = __key;
            }
        }
        return placeEntity;
    }

    public void setPlaceEntity(PlaceEntity placeEntity) {
        synchronized (this) {
            this.placeEntity = placeEntity;
            placeId = placeEntity == null ? null : placeEntity.getLocal_id();
            placeEntity__resolvedKey = placeId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
