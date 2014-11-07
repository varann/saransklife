package ru.saransklife.dao;

import ru.saransklife.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EVENT.
 */
public class Event {

    private Long id;
    private String name;
    private String description;
    private String story;
    private java.util.Date start_date;
    private java.util.Date end_date;
    private String time_type;
    private Boolean is_repeatable;
    private String photo_author;
    private String photo_path;
    private String price;
    private Long category_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient EventDao myDao;

    private EventCategory eventCategory;
    private Long eventCategory__resolvedKey;


    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, String name, String description, String story, java.util.Date start_date, java.util.Date end_date, String time_type, Boolean is_repeatable, String photo_author, String photo_path, String price, Long category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.story = story;
        this.start_date = start_date;
        this.end_date = end_date;
        this.time_type = time_type;
        this.is_repeatable = is_repeatable;
        this.photo_author = photo_author;
        this.photo_path = photo_path;
        this.price = price;
        this.category_id = category_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
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

    public java.util.Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.util.Date start_date) {
        this.start_date = start_date;
    }

    public java.util.Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(java.util.Date end_date) {
        this.end_date = end_date;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public Boolean getIs_repeatable() {
        return is_repeatable;
    }

    public void setIs_repeatable(Boolean is_repeatable) {
        this.is_repeatable = is_repeatable;
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
