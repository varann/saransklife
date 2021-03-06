package ru.saransklife.dao;

import java.util.List;
import ru.saransklife.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SECTION_ITEM.
 */
public class SectionItem {

    private Long id;
    private String name;
    private String slug;
    private String module;
    private Long parentId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient SectionItemDao myDao;

    private List<SectionItem> children;

    public SectionItem() {
    }

    public SectionItem(Long id) {
        this.id = id;
    }

    public SectionItem(Long id, String name, String slug, String module, Long parentId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.module = module;
        this.parentId = parentId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSectionItemDao() : null;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<SectionItem> getChildren() {
        if (children == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SectionItemDao targetDao = daoSession.getSectionItemDao();
            List<SectionItem> childrenNew = targetDao._querySectionItem_Children(id);
            synchronized (this) {
                if(children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetChildren() {
        children = null;
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
