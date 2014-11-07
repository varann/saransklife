package ru.saransklife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.ApiEvent;
import ru.saransklife.api.model.ApiSectionItem;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
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
import ru.saransklife.dao.SectionItem;
import ru.saransklife.dao.SectionItemDao;
import ru.saransklife.menu.SectionItemType;

/**
 * Created by asavinova on 30/10/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Dao {

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

		//TODO Добавить проверку существования типа раздела меню

		for (ApiSectionItem apiItem : menuItems) {
			if (SectionItemType.PAGE.getModule().equals(apiItem.getModule())) {
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
		return builder.where(SectionItemDao.Properties.Module.eq(SectionItemType.PAGE.getModule())).build().list();
	}

	public Page getPage(String slug) {
		PageDao pageDao = daoSession.getPageDao();
		QueryBuilder<Page> builder = pageDao.queryBuilder();
		Page page = builder.where(PageDao.Properties.Slug.eq(slug)).build().unique();
		if (page != null) {
			return page;
		} else {
			PageResponse response = api.getPage(slug);
			long id = pageDao.insert(response.getResponse());
			return pageDao.load(id);
		}
	}

	public Cursor getPlaceCategoryCursor() {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		return db.query(categoryDao.getTablename(), categoryDao.getAllColumns(), null, null, null, null, null);
	}

	public void setPlaceCategories(List<PlaceCategory> categories) {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		categoryDao.deleteAll();
		categoryDao.insertInTx(categories);
	}

	public Cursor getPlaceEntitiesCursor() {
		PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
		return db.query(entitiesDao.getTablename(), entitiesDao.getAllColumns(), null, null, null, null, null);
	}

	public PlaceCategory getPlaceCategoryById(long categoryId) {
		PlaceCategoryDao placeCategoryDao = daoSession.getPlaceCategoryDao();
		QueryBuilder<PlaceCategory> builder = placeCategoryDao.queryBuilder();
		return builder.where(PlaceCategoryDao.Properties.Id.eq(categoryId)).build().unique();
	}

	public void setPlaceEntities(List<PlaceEntity> entities) {
		PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
		entitiesDao.deleteAll();
		entitiesDao.insertInTx(entities);
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

	public List<EventCategory> getEventCategories() {
		//TODO Изменить запрос, чтобы выдавались только категории, у которых есть события
		EventCategoryDao categoryDao = daoSession.getEventCategoryDao();
		return categoryDao.loadAll();
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
}
