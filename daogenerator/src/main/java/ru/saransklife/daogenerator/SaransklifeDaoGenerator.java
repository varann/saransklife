package ru.saransklife.daogenerator;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Created by asavinova on 26/10/14.
 */
public class SaransklifeDaoGenerator {

	public static void main(String[] args) throws Exception {

		Schema schema = new Schema(1, "ru.saransklife.dao");

		Entity sectionItem = schema.addEntity("SectionItem");
		sectionItem.addIdProperty();
		sectionItem.addStringProperty("name");
		sectionItem.addStringProperty("slug");
		sectionItem.addStringProperty("module");

		Property parentId = sectionItem.addLongProperty("parentId").getProperty();
		ToMany itemToItems = sectionItem.addToMany(sectionItem, parentId);
		itemToItems.setName("children");

		Entity page = schema.addEntity("Page");
		page.addIdProperty();
		page.addStringProperty("title");
		page.addStringProperty("text");
		page.addStringProperty("slug").unique();

		Entity placeCategory = schema.addEntity("PlaceCategory");
		placeCategory.addIdProperty();
		placeCategory.addStringProperty("name");
		placeCategory.addStringProperty("slug").unique();

		Entity entity = schema.addEntity("PlaceEntity");
		entity.addIdProperty();
		entity.addStringProperty("slug");
		entity.addStringProperty("name");
		entity.addStringProperty("address");
		entity.addStringProperty("phone");
		entity.addStringProperty("email");
		entity.addStringProperty("website");
		entity.addFloatProperty("latitude");
		entity.addFloatProperty("longitude");
		entity.addStringProperty("description");
		entity.addStringProperty("photo_author");
		entity.addStringProperty("photo_path");
		entity.addStringProperty("information");
		entity.addStringProperty("working_time");
		entity.addFloatProperty("rating");
		entity.addIntProperty("view_count");
		entity.addIntProperty("recommended_count");

//		Property placeCategorySlug = entity.addStringProperty("slug").getProperty();
//		entity.addToOne(placeCategory, placeCategorySlug);


		Entity eventCategory = schema.addEntity("EventCategory");
		eventCategory.addIdProperty();
		eventCategory.addStringProperty("name");
		eventCategory.addStringProperty("slug").unique();

		Entity event = schema.addEntity("Event");
		event.addIdProperty();
		event.addStringProperty("name");
		event.addStringProperty("description");
		event.addStringProperty("story");
		event.addDateProperty("start_date");
		event.addDateProperty("end_date");
		event.addStringProperty("time_type");
		event.addBooleanProperty("is_repeatable");
		event.addStringProperty("photo_author");
		event.addStringProperty("photo_path");
		event.addStringProperty("price");

		//TODO Добавить сеансы к событию

		Property categoryId = event.addLongProperty("category_id").getProperty();
		event.addToOne(eventCategory, categoryId);


		Entity refCategory = schema.addEntity("ReferenceCategory");
		refCategory.addIdProperty();
		refCategory.addStringProperty("name");
		refCategory.addStringProperty("description");
		refCategory.addStringProperty("slug").unique();

		Entity reference = schema.addEntity("Reference");
		reference.addIdProperty();
		reference.addStringProperty("slug");
		reference.addStringProperty("name");
		reference.addStringProperty("description");
		reference.addStringProperty("phone");
		reference.addStringProperty("information");
		reference.addStringProperty("address");
		reference.addStringProperty("site");


		Entity cache = schema.addEntity("CacheInfo");
		cache.addIdProperty();
		cache.addStringProperty("request");
		cache.addStringProperty("params");
		cache.addDateProperty("last_updated");


		String outDir = "app/src/dao/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}
}
