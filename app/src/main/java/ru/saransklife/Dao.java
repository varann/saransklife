package ru.saransklife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.ApiSectionItem;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.dao.DaoMaster;
import ru.saransklife.dao.DaoSession;
import ru.saransklife.dao.Page;
import ru.saransklife.dao.PageDao;
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
}
