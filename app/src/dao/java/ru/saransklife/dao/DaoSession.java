package ru.saransklife.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import ru.saransklife.dao.SectionItem;
import ru.saransklife.dao.Page;
import ru.saransklife.dao.PlaceCategory;

import ru.saransklife.dao.SectionItemDao;
import ru.saransklife.dao.PageDao;
import ru.saransklife.dao.PlaceCategoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sectionItemDaoConfig;
    private final DaoConfig pageDaoConfig;
    private final DaoConfig placeCategoryDaoConfig;

    private final SectionItemDao sectionItemDao;
    private final PageDao pageDao;
    private final PlaceCategoryDao placeCategoryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sectionItemDaoConfig = daoConfigMap.get(SectionItemDao.class).clone();
        sectionItemDaoConfig.initIdentityScope(type);

        pageDaoConfig = daoConfigMap.get(PageDao.class).clone();
        pageDaoConfig.initIdentityScope(type);

        placeCategoryDaoConfig = daoConfigMap.get(PlaceCategoryDao.class).clone();
        placeCategoryDaoConfig.initIdentityScope(type);

        sectionItemDao = new SectionItemDao(sectionItemDaoConfig, this);
        pageDao = new PageDao(pageDaoConfig, this);
        placeCategoryDao = new PlaceCategoryDao(placeCategoryDaoConfig, this);

        registerDao(SectionItem.class, sectionItemDao);
        registerDao(Page.class, pageDao);
        registerDao(PlaceCategory.class, placeCategoryDao);
    }
    
    public void clear() {
        sectionItemDaoConfig.getIdentityScope().clear();
        pageDaoConfig.getIdentityScope().clear();
        placeCategoryDaoConfig.getIdentityScope().clear();
    }

    public SectionItemDao getSectionItemDao() {
        return sectionItemDao;
    }

    public PageDao getPageDao() {
        return pageDao;
    }

    public PlaceCategoryDao getPlaceCategoryDao() {
        return placeCategoryDao;
    }

}
