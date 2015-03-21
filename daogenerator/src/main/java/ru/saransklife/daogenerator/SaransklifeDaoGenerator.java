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

		addSectionItemEntity(schema);

		addPageEntity(schema);

		addPlaceCategoryEntity(schema);
		Entity place = addPlaceEntity(schema);

		Entity seance = addSeanceEntity(schema, place);

		Entity eventCategory = addEventCategoryEntity(schema);
		Entity params = addEventParamsEntity(schema);
		addEventEntity(schema, eventCategory, seance, place, params);

		addReferenceCategoryEntity(schema);
		addReferenceEntity(schema);

		addCacheInfoEntity(schema);


		String outDir = "app/src/dao/java";
		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}

	private static void addSectionItemEntity(Schema schema) {
		Entity sectionItem = schema.addEntity("SectionItem");
		sectionItem.addIdProperty();
		sectionItem.addStringProperty("name");
		sectionItem.addStringProperty("slug");
		sectionItem.addStringProperty("module");

		Property parentId = sectionItem.addLongProperty("parentId").getProperty();
		ToMany itemToItems = sectionItem.addToMany(sectionItem, parentId);
		itemToItems.setName("children");
	}

	private static void addPageEntity(Schema schema) {
		Entity page = schema.addEntity("Page");
		page.addIdProperty();
		page.addStringProperty("title");
		page.addStringProperty("text");
		page.addStringProperty("slug").unique();
	}

	private static void addPlaceCategoryEntity(Schema schema) {
		Entity placeCategory = schema.addEntity("PlaceCategory");
		placeCategory.addIdProperty();
		placeCategory.addStringProperty("name");
		placeCategory.addStringProperty("slug").unique();
		placeCategory.addStringProperty("parent_slug");
	}

	private static Entity addPlaceEntity(Schema schema) {
		Entity entity = schema.addEntity("PlaceEntity");
		entity.addLongProperty("local_id").primaryKey();
		entity.addLongProperty("id");
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
		entity.addFloatProperty("rating");
		entity.addIntProperty("view_count");
		entity.addIntProperty("recommended_count");

//		Property placeCategorySlug = entity.addStringProperty("slug").getProperty();
//		entity.addToOne(placeCategory, placeCategorySlug);
		return entity;
	}

	private static Entity addSeanceEntity(Schema schema, Entity place) {
		Entity seance = schema.addEntity("Seance");
		seance.addIdProperty();
		seance.addDateProperty("datetime");
		seance.addStringProperty("hallName");
		seance.addStringProperty("type");
		seance.addIntProperty("price");
		seance.addLongProperty("placeId");

		return seance;
	}

	private static Entity addEventCategoryEntity(Schema schema) {
		Entity eventCategory = schema.addEntity("EventCategory");
		eventCategory.addIdProperty();
		eventCategory.addStringProperty("name");
		eventCategory.addStringProperty("slug").unique();
		return eventCategory;
	}

	private static Entity addEventParamsEntity(Schema schema) {
		Entity params = schema.addEntity("EventParams");
		params.addIdProperty();
		params.addStringProperty("english_name");
		params.addStringProperty("description");
		params.addStringProperty("country");
		params.addStringProperty("year");
		params.addStringProperty("genre");
		params.addStringProperty("duration");
		params.addStringProperty("start_age");
		params.addStringProperty("director");
		params.addStringProperty("actors");
		params.addStringProperty("afisha");
		params.addStringProperty("json_images");
		return params;
	}

	private static void addEventEntity(Schema schema, Entity eventCategory, Entity seance, Entity place, Entity params) {
		Entity event = schema.addEntity("Event");
		event.addLongProperty("local_id").primaryKey();
		event.addLongProperty("id");
		event.addStringProperty("type");
		event.addStringProperty("name");
		event.addStringProperty("description");
		event.addStringProperty("story");
		event.addStringProperty("photo_author");
		event.addStringProperty("photo_path");
		event.addStringProperty("price");

		Property categoryId = event.addLongProperty("category_id").getProperty();
		event.addToOne(eventCategory, categoryId);
		{
			Property eventId = seance.addLongProperty("event_id").notNull().getProperty();
			event.addToMany(seance, eventId).setName("seances");
		}
		{
			Property eventId = place.addLongProperty("event_id").notNull().getProperty();
			event.addToMany(place, eventId).setName("places");
		}
		{
			Property paramsId = event.addLongProperty("params_id").getProperty();
			event.addToOne(params, paramsId).setName("params");
		}
	}

	private static void addReferenceCategoryEntity(Schema schema) {
		Entity refCategory = schema.addEntity("ReferenceCategory");
		refCategory.addIdProperty();
		refCategory.addStringProperty("name");
		refCategory.addStringProperty("description");
		refCategory.addStringProperty("slug").unique();
	}

	private static void addReferenceEntity(Schema schema) {
		Entity reference = schema.addEntity("Reference");
		reference.addIdProperty();
		reference.addStringProperty("slug");
		reference.addStringProperty("name");
		reference.addStringProperty("description");
		reference.addStringProperty("phone");
		reference.addStringProperty("information");
		reference.addStringProperty("address");
		reference.addStringProperty("site");
	}

	private static void addCacheInfoEntity(Schema schema) {
		Entity cache = schema.addEntity("CacheInfo");
		cache.addIdProperty();
		cache.addStringProperty("request");
		cache.addStringProperty("params");
		cache.addDateProperty("last_updated");
	}
}
