package ru.saransklife.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table REFERENCE_CATEGORY.
 */
public class ReferenceCategoryDao extends AbstractDao<ReferenceCategory, Long> {

	public static final String TABLENAME = "REFERENCE_CATEGORY";

	/**
	 * Properties of entity ReferenceCategory.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id", true, "_id");
		public final static Property Name = new Property(1, String.class, "name", false, "NAME");
		public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
		public final static Property Slug = new Property(3, String.class, "slug", false, "SLUG");
	}

	;


	public ReferenceCategoryDao(DaoConfig config) {
		super(config);
	}

	public ReferenceCategoryDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/**
	 * Creates the underlying database table.
	 */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'REFERENCE_CATEGORY' (" + //
				"'_id' INTEGER PRIMARY KEY ," + // 0: id
				"'NAME' TEXT," + // 1: name
				"'DESCRIPTION' TEXT," + // 2: description
				"'SLUG' TEXT UNIQUE );"); // 3: slug
	}

	/**
	 * Drops the underlying database table.
	 */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'REFERENCE_CATEGORY'";
		db.execSQL(sql);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected void bindValues(SQLiteStatement stmt, ReferenceCategory entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}

		String name = entity.getName();
		if (name != null) {
			stmt.bindString(2, name);
		}

		String description = entity.getDescription();
		if (description != null) {
			stmt.bindString(3, description);
		}

		String slug = entity.getSlug();
		if (slug != null) {
			stmt.bindString(4, slug);
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public ReferenceCategory readEntity(Cursor cursor, int offset) {
		ReferenceCategory entity = new ReferenceCategory( //
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
				cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
				cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // slug
		);
		return entity;
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void readEntity(Cursor cursor, ReferenceCategory entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
		entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
		entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
		entity.setSlug(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected Long updateKeyAfterInsert(ReferenceCategory entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public Long getKey(ReferenceCategory entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
