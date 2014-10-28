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

		Entity menuItem = schema.addEntity("MenuItem");
		menuItem.addIdProperty();
		menuItem.addStringProperty("name");
		menuItem.addStringProperty("slug");
		menuItem.addStringProperty("module");

		Property parentId = menuItem.addLongProperty("parentId").getProperty();
		ToMany itemToItems = menuItem.addToMany(menuItem, parentId);
		itemToItems.setName("children");

		String outDir = "app/src/dao/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}
}
