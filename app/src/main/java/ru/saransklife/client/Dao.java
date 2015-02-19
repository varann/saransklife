package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.ApiEvent;
import ru.saransklife.api.model.ApiSectionItem;
import ru.saransklife.client.drawer.SectionItemType;
import ru.saransklife.dao.CacheInfo;
import ru.saransklife.dao.CacheInfoDao;
import ru.saransklife.dao.DaoMaster;
import ru.saransklife.dao.DaoSession;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.EventCategoryDao;
import ru.saransklife.dao.EventDao;
import ru.saransklife.dao.Page;
import ru.saransklife.dao.PageDao;
import ru.saransklife.dao.PlaceCategory;
import ru.saransklife.dao.PlaceCategoryDao;
import ru.saransklife.dao.PlaceEntity;
import ru.saransklife.dao.PlaceEntityDao;
import ru.saransklife.dao.Reference;
import ru.saransklife.dao.ReferenceCategory;
import ru.saransklife.dao.ReferenceCategoryDao;
import ru.saransklife.dao.ReferenceDao;
import ru.saransklife.dao.SectionItem;
import ru.saransklife.dao.SectionItemDao;

/**
 * Created by asavinova on 30/10/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Dao {

	public static final String INTERESTING_PLACES_SLUG = "interesting";
	public static final String ABOUT_PAGE_SLUG = "about";

	public enum Request {
		PLACE_CATEGORIES
	}

	private SQLiteDatabase db;
	@RootContext Context context;
	@RestService RestApiClient api;

	private DaoMaster.DevOpenHelper helper;
	private DaoMaster daoMaster;
	private DaoSession daoSession;

	@AfterInject
	void init() {
		helper = new DaoMaster.DevOpenHelper(context, "saransklife-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	public void setMenuItems(List<ApiSectionItem> menuItems) {
		SectionItemDao sectionItemDao = daoSession.getSectionItemDao();
		sectionItemDao.deleteAll();

		SectionItem mainItem = new SectionItem();
		mainItem.setId(0l);
		mainItem.setModule(SectionItemType.MAIN.name());
		mainItem.setName(context.getString(R.string.main_menu_item_title));
		sectionItemDao.insert(mainItem);

		for (ApiSectionItem apiItem : menuItems) {
			if (SectionItemType.PAGE.name().equalsIgnoreCase(apiItem.getModule())) {
				long itemId = sectionItemDao.insert(apiItem);
				List<ApiSectionItem> pageItemChild = apiItem.getChild();
				for (ApiSectionItem apiChild : pageItemChild) {
					apiChild.setParentId(itemId);
					sectionItemDao.insert(apiChild);
				}
			} else {
				sectionItemDao.insert(apiItem);
			}
		}
	}

	public List<SectionItem> getRootMenuItems() {
		QueryBuilder<SectionItem> builder = daoSession.getSectionItemDao().queryBuilder();
		return builder.where(SectionItemDao.Properties.ParentId.isNull()).build().list();
	}

	public List<SectionItem> getPageSectionItems() {
		QueryBuilder<SectionItem> builder = daoSession.getSectionItemDao().queryBuilder();
		return builder.where(SectionItemDao.Properties.Module.eq(SectionItemType.PAGE.name().toLowerCase())).build().list();
	}

	public Page getPage(String slug) {
		PageDao pageDao = daoSession.getPageDao();
		QueryBuilder<Page> builder = pageDao.queryBuilder();
		return builder.where(PageDao.Properties.Slug.eq(slug)).build().unique();
	}

	public void setPage(Page page) {
		PageDao pageDao = daoSession.getPageDao();
		pageDao.insert(page);
	}

	public Cursor getPlaceCategoryCursor() {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		return db.query(categoryDao.getTablename(),
				categoryDao.getAllColumns(),
				PlaceCategoryDao.Properties.Slug.columnName + " != '" + INTERESTING_PLACES_SLUG + "'", null, null, null, null);
	}

	public void setPlaceCategories(List<PlaceCategory> categories) {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		categoryDao.deleteAll();
		categoryDao.insertInTx(categories);

		setLastUpdated(Dao.Request.PLACE_CATEGORIES);
	}

	public Cursor getPlaceEntitiesBySlugCursor(String slug) {
		return db.rawQuery("SELECT * FROM " + PlaceEntityDao.TABLENAME +
				" WHERE " + PlaceEntityDao.Properties.Slug.columnName
				+ " = '" + slug + "'", null);
	}

	public PlaceCategory getPlaceCategoryById(long categoryId) {
		PlaceCategoryDao placeCategoryDao = daoSession.getPlaceCategoryDao();
		QueryBuilder<PlaceCategory> builder = placeCategoryDao.queryBuilder();
		return builder.where(PlaceCategoryDao.Properties.Id.eq(categoryId)).build().unique();
	}

	public void setPlaceEntities(List<PlaceEntity> entities, String slug) {
		PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
		QueryBuilder<PlaceEntity> builder = entitiesDao.queryBuilder().where(PlaceEntityDao.Properties.Slug.eq(slug));
		List<PlaceEntity> oldEntities = builder.build().list();
		entitiesDao.deleteInTx(oldEntities);
		for (PlaceEntity entity : entities) {
			entity.setSlug(slug);
			entitiesDao.insertOrReplace(entity);
		}
	}

	public PlaceEntity getPlaceEntity(long id) {
		PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
		QueryBuilder<PlaceEntity> builder = entitiesDao.queryBuilder().where(PlaceEntityDao.Properties.Id.eq(id));
		return builder.build().unique();
	}

	public void setEventCategories(List<EventCategory> categories) {
		EventCategoryDao categoryDao = daoSession.getEventCategoryDao();
		categoryDao.deleteAll();
		categoryDao.insertInTx(categories);
	}

	public void setEvents(List<ApiEvent> apiEvents) {
		PlaceEntityDao placeEntityDao = daoSession.getPlaceEntityDao();

		EventDao eventDao = daoSession.getEventDao();
		eventDao.deleteAll();
		for (ApiEvent apiEvent : apiEvents) {
			eventDao.insert(apiEvent);
			List<PlaceEntity> places = apiEvent.getPlaces();
			if (places != null && !places.isEmpty()) {
				placeEntityDao.insertOrReplaceInTx(places);
			}
		}
	}

	public Cursor getEventCategories() {
		Cursor query = db.rawQuery("SELECT DISTINCT " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Id.columnName + ", " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Name.columnName + ", " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Slug.columnName +
				" FROM " + EventCategoryDao.TABLENAME +
				" INNER JOIN " + EventDao.TABLENAME +
				" ON " + EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Id.columnName +
				" = " + EventDao.Properties.Category_id.columnName, null);

		return query;
	}

	public List<Event> getEventsByCategory(long categoryId) {
		EventDao eventDao = daoSession.getEventDao();
		QueryBuilder<Event> builder = eventDao.queryBuilder().where(EventDao.Properties.Category_id.eq(categoryId));
		return builder.build().list();
	}

	public Event getEventById(long id) {
		EventDao eventDao = daoSession.getEventDao();
		QueryBuilder<Event> builder = eventDao.queryBuilder().where(EventDao.Properties.Id.eq(id));
		return builder.build().unique();
	}

	public EventCategory getEventCategoryById(Long categoryId) {
		EventCategoryDao categoryDao = daoSession.getEventCategoryDao();
		QueryBuilder<EventCategory> builder = categoryDao.queryBuilder().where(EventCategoryDao.Properties.Id.eq(categoryId));
		return builder.build().unique();
	}

	public void setReferenceCategories(List<ReferenceCategory> categories) {
		ReferenceCategoryDao refCategoriesDao = daoSession.getReferenceCategoryDao();
		refCategoriesDao.deleteAll();
		refCategoriesDao.insertInTx(categories);
	}

	public List<ReferenceCategory> getReferenceCategories() {
		ReferenceCategoryDao refCategoriesDao = daoSession.getReferenceCategoryDao();
		return refCategoriesDao.loadAll();
	}

	public ReferenceCategory getReferenceCategoryBySlug(String slug) {
		ReferenceCategoryDao refCategoriesDao = daoSession.getReferenceCategoryDao();
		QueryBuilder<ReferenceCategory> builder = refCategoriesDao.queryBuilder().where(ReferenceCategoryDao.Properties.Slug.eq(slug));
		return builder.build().unique();
	}

	public void setReferences(List<Reference> references, String slug) {
		ReferenceDao referenceDao = daoSession.getReferenceDao();
		QueryBuilder<Reference> builder = referenceDao.queryBuilder().where(ReferenceDao.Properties.Slug.eq(slug));
		List<Reference> oldEntities = builder.build().list();
		referenceDao.deleteInTx(oldEntities);
		for (Reference reference : references) {
			reference.setSlug(slug);
			referenceDao.insertOrReplace(reference);
		}
	}

	public List<Reference> getReferences(String slug) {
		ReferenceDao referenceDao = daoSession.getReferenceDao();
		QueryBuilder<Reference> builder = referenceDao.queryBuilder().where(ReferenceDao.Properties.Slug.eq(slug));
		return builder.build().list();
	}

	public Reference getReference(long id) {
		ReferenceDao referenceDao = daoSession.getReferenceDao();
		QueryBuilder<Reference> builder = referenceDao.queryBuilder().where(ReferenceDao.Properties.Id.eq(id));
		return builder.build().unique();
	}

	public void setLastUpdated(Request request) {
		CacheInfoDao cacheInfoDao = daoSession.getCacheInfoDao();
		CacheInfo cacheInfo = new CacheInfo();
		cacheInfo.setRequest(request.name());
		cacheInfo.setLast_updated(new Date());
		cacheInfoDao.insertOrReplace(cacheInfo);
	}

	public Date getLastUpdated(Request request) {
		CacheInfoDao cacheInfoDao = daoSession.getCacheInfoDao();
		QueryBuilder<CacheInfo> builder = cacheInfoDao.queryBuilder().where(CacheInfoDao.Properties.Request.eq(request.name()));
		CacheInfo cacheInfo = builder.build().unique();
		if (cacheInfo == null) {
			return null;
		}
		return cacheInfo.getLast_updated();
	}
}
