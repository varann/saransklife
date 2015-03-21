package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import ru.saransklife.R;
import ru.saransklife.api.model.ApiEvent;
import ru.saransklife.api.model.ApiEventParams;
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
import ru.saransklife.dao.EventParamsDao;
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
import ru.saransklife.dao.Seance;
import ru.saransklife.dao.SeanceDao;
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
		INTERESTING_PLACES,
		PLACE_CATEGORIES,
		PLACE_ENTITIES,
		EVENTS,
		REFERENCE_CATEGORIES,
		REFERENCES
	}

	private SQLiteDatabase db;
	@RootContext Context context;

	private DaoMaster.DevOpenHelper helper;
	private DaoMaster daoMaster;
	private DaoSession daoSession;

	private Gson gson = new GsonBuilder().create();

	@AfterInject
	void init() {
		helper = new DaoMaster.DevOpenHelper(context, "saransklife-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	public void setMenuItems(final List<ApiSectionItem> menuItems) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

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
		});
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
		pageDao.insertOrReplace(page);
	}

	public Cursor getPlaceCategoryCursor() {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		return db.query(categoryDao.getTablename(),
				categoryDao.getAllColumns(),
				PlaceCategoryDao.Properties.Parent_slug.columnName + " IS NULL",
				null, null, null, null);
	}

	public List<PlaceCategory> getSubPlaceCategories(String slug) {
		PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
		return categoryDao.queryBuilder().where(PlaceCategoryDao.Properties.Parent_slug.eq(slug)).list();
	}

	public void setPlaceCategories(final List<PlaceCategory> categories, final String slug) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

				PlaceCategoryDao categoryDao = daoSession.getPlaceCategoryDao();
				List<PlaceCategory> oldCategories;
				if (slug == null) {
					oldCategories = categoryDao.queryBuilder().where(PlaceCategoryDao.Properties.Parent_slug.isNull()).build().list();
				} else {
					oldCategories = categoryDao.queryBuilder().where(PlaceCategoryDao.Properties.Parent_slug.eq(slug)).build().list();
					for (PlaceCategory category : categories) {
						category.setParent_slug(slug);
					}
				}
				categoryDao.deleteInTx(oldCategories);
				categoryDao.insertOrReplaceInTx(categories);

				setLastUpdated(Dao.Request.PLACE_CATEGORIES, slug);

			}
		});
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

	public void setPlaceEntities(final List<PlaceEntity> entities, final Request request, final String slug) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

				PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
				List<PlaceEntity> oldEntities = entitiesDao.queryBuilder().where(PlaceEntityDao.Properties.Slug.eq(slug)).build().list();
				entitiesDao.deleteInTx(oldEntities);
				for (PlaceEntity entity : entities) {
					entity.setSlug(slug);
					entitiesDao.insertOrReplace(entity);
				}

				setLastUpdated(request, slug);

			}
		});
	}

	public PlaceEntity getPlaceEntity(long id) {
		PlaceEntityDao entitiesDao = daoSession.getPlaceEntityDao();
		QueryBuilder<PlaceEntity> builder = entitiesDao.queryBuilder().where(PlaceEntityDao.Properties.Id.eq(id)).limit(1);
		return builder.build().unique();
	}

	public void setEventCategory(EventCategory category) {
		EventCategoryDao categoryDao = daoSession.getEventCategoryDao();
		categoryDao.insertOrReplace(category);
	}

	public void setEvents(final List<ApiEvent> apiEvents, final String type) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

				PlaceEntityDao placeEntityDao = daoSession.getPlaceEntityDao();
				EventDao eventDao = daoSession.getEventDao();
				SeanceDao seanceDao = daoSession.getSeanceDao();
				EventParamsDao paramsDao = daoSession.getEventParamsDao();

				// Удаляем старые события с параметрами, сеансы к ним и привязанные места
				List<Event> oldEvents = eventDao.queryBuilder().where(EventDao.Properties.Type.eq(type)).build().list();
				for (Event oldEvent : oldEvents) {
					if (oldEvent.getParams() != null) {
						paramsDao.delete(oldEvent.getParams());
					}
					seanceDao.deleteInTx(oldEvent.getSeances());
					placeEntityDao.deleteInTx(oldEvent.getPlaces());

				}
				eventDao.deleteInTx(oldEvents);

				for (ApiEvent apiEvent : apiEvents) {
					// Сохранение категории события
					setEventCategory(apiEvent.getCategory());

					// Сохранение параметров
					ApiEventParams params = apiEvent.getParams();
					if (params != null) {
						params.setJson_images(gson.toJson(params.getImagesArray()));
						long paramsId = paramsDao.insertOrReplace(params);
						apiEvent.setParams_id(paramsId);
					}

					apiEvent.setType(type);
					long id = eventDao.insert(apiEvent);

					// Сохранение сеансов
					List<Seance> seancesObjects = apiEvent.getSeancesData().getSeancesObjects();
					for (Seance seance : seancesObjects) {
						seance.setEvent_id(id);
					}
					seanceDao.insertOrReplaceInTx(seancesObjects);

					// Сохранение мест
					List<PlaceEntity> places = apiEvent.getPlaces();
					for (PlaceEntity place : places) {
						place.setEvent_id(id);
					}
					placeEntityDao.insertOrReplaceInTx(places);
				}

				setLastUpdated(Dao.Request.EVENTS, type);
			}
		});
	}

	public Cursor getEventCategories(String type) {
		Cursor query = db.rawQuery("SELECT DISTINCT " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Id.columnName + ", " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Name.columnName + ", " +
				EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Slug.columnName +
				" FROM " + EventCategoryDao.TABLENAME +
				" INNER JOIN " + EventDao.TABLENAME +
				" ON " + EventCategoryDao.TABLENAME + "." + EventCategoryDao.Properties.Id.columnName +
				" = " + EventDao.Properties.Category_id.columnName +
				" WHERE " + EventDao.Properties.Type.columnName + " == '" + type + "'", null);

		return query;
	}

	public List<Event> getEventsByCategory(long categoryId, String type) {
		EventDao eventDao = daoSession.getEventDao();
		QueryBuilder<Event> builder = eventDao.queryBuilder()
				.where(EventDao.Properties.Category_id.eq(categoryId),
						EventDao.Properties.Type.eq(type));
		return builder.build().list();
	}

	public Event getEventById(long id) {
		EventDao eventDao = daoSession.getEventDao();
		QueryBuilder<Event> builder = eventDao.queryBuilder().where(EventDao.Properties.Id.eq(id)).limit(1);
		return builder.build().unique();
	}

	public EventCategory getEventCategoryById(Long categoryId) {
		EventCategoryDao categoryDao = daoSession.getEventCategoryDao();
		QueryBuilder<EventCategory> builder = categoryDao.queryBuilder().where(EventCategoryDao.Properties.Id.eq(categoryId));
		return builder.build().unique();
	}

	public void setReferenceCategories(final List<ReferenceCategory> categories) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

				ReferenceCategoryDao refCategoriesDao = daoSession.getReferenceCategoryDao();
				refCategoriesDao.deleteAll();
				refCategoriesDao.insertInTx(categories);

				setLastUpdated(Request.REFERENCE_CATEGORIES, null);

			}
		});
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

	public void setReferences(final List<Reference> references, final String slug) {
		daoSession.runInTx(new Runnable() {
			@Override
			public void run() {

				ReferenceDao referenceDao = daoSession.getReferenceDao();
				QueryBuilder<Reference> builder = referenceDao.queryBuilder().where(ReferenceDao.Properties.Slug.eq(slug));
				List<Reference> oldEntities = builder.build().list();
				referenceDao.deleteInTx(oldEntities);
				for (Reference reference : references) {
					reference.setSlug(slug);
					referenceDao.insertOrReplace(reference);
				}

				setLastUpdated(Request.REFERENCES, slug);

			}
		});
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

	public void setLastUpdated(Request request, String params) {
		CacheInfoDao cacheInfoDao = daoSession.getCacheInfoDao();
		CacheInfo cacheInfo = getCacheInfo(request, params);
		if (cacheInfo == null) {
			cacheInfo = new CacheInfo();
			cacheInfo.setRequest(request.name());
			cacheInfo.setParams(params);
		}
		cacheInfo.setLast_updated(new Date());
		cacheInfoDao.insertOrReplace(cacheInfo);
	}

	public Date getLastUpdated(Request request, String params) {
		CacheInfo cacheInfo = getCacheInfo(request, params);
		if (cacheInfo == null) {
			return null;
		}
		return cacheInfo.getLast_updated();
	}

	private CacheInfo getCacheInfo(Request request, String params) {
		CacheInfoDao cacheInfoDao = daoSession.getCacheInfoDao();
		WhereCondition condition;
		if (params == null) {
			condition = CacheInfoDao.Properties.Params.isNull();
		} else {
			condition = CacheInfoDao.Properties.Params.eq(params);
		}
		QueryBuilder<CacheInfo> builder = cacheInfoDao.queryBuilder()
				.where(CacheInfoDao.Properties.Request.eq(request.name()), condition);
		return builder.build().unique();
	}
}
