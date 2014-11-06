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

		Entity category = schema.addEntity("PlaceCategory");
		category.addIdProperty();
		category.addStringProperty("name");
		category.addStringProperty("slug").unique();

		Entity entity = schema.addEntity("PlaceEntity");
		entity.addIdProperty();
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


		String outDir = "app/src/dao/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}
}
