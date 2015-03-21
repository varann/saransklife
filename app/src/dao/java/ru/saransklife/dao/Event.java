package ru.saransklife.dao;

import java.util.List;
import ru.saransklife.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EVENT.
 */
public class Event {

    private Long local_id;
    private Long id;
    private String type;
    private String name;
    private String description;
    private String story;
    private String photo_author;
    private String photo_path;
    private String price;
    private Long category_id;
    private Long params_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient EventDao myDao;

    private EventCategory eventCategory;
    private Long eventCategory__resolvedKey;

    private EventParams params;
    private Long params__resolvedKey;

    private List<Seance> seances;
    private List<PlaceEntity> places;

    public Event() {
    }

    public Event(Long local_id) {
        this.local_id = local_id;
    }

    public Event(Long local_id, Long id, String type, String name, String description, String story, String photo_author, String photo_path, String price, Long category_id, Long params_id) {
        this.local_id = local_id;
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.story = story;
        this.photo_author = photo_author;
        this.photo_path = photo_path;
        this.price = price;
        this.category_id = category_id;
        this.params_id = params_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }

    public Long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Long local_id) {
        this.local_id = local_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getParams_id() {
        return params_id;
    }

    public void setParams_id(Long params_id) {
        this.params_id = params_id;
    }

    /** To-one relationship, resolved on first access. */
    public EventCategory getEventCategory() {
        Long __key = this.category_id;
        if (eventCategory__resolvedKey == null || !eventCategory__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EventCategoryDao targetDao = daoSession.getEventCategoryDao();
            EventCategory eventCategoryNew = targetDao.load(__key);
            synchronized (this) {
                eventCategory = eventCategoryNew;
            	eventCategory__resolvedKey = __key;
            }
        }
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        synchronized (this) {
            this.eventCategory = eventCategory;
            category_id = eventCategory == null ? null : eventCategory.getId();
            eventCategory__resolvedKey = category_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public EventParams getParams() {
        Long __key = this.params_id;
        if (params__resolvedKey == null || !params__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EventParamsDao targetDao = daoSession.getEventParamsDao();
            EventParams paramsNew = targetDao.load(__key);
            synchronized (this) {
                params = paramsNew;
            	params__resolvedKey = __key;
            }
        }
        return params;
    }

    public void setParams(EventParams params) {
        synchronized (this) {
            this.params = params;
            params_id = params == null ? null : params.getId();
            params__resolvedKey = params_id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Seance> getSeances() {
        if (seances == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SeanceDao targetDao = daoSession.getSeanceDao();
            List<Seance> seancesNew = targetDao._queryEvent_Seances(local_id);
            synchronized (this) {
                if(seances == null) {
                    seances = seancesNew;
                }
            }
        }
        return seances;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetSeances() {
        seances = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<PlaceEntity> getPlaces() {
        if (places == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlaceEntityDao targetDao = daoSession.getPlaceEntityDao();
            List<PlaceEntity> placesNew = targetDao._queryEvent_Places(local_id);
            synchronized (this) {
                if(places == null) {
                    places = placesNew;
                }
            }
        }
        return places;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetPlaces() {
        places = null;
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
