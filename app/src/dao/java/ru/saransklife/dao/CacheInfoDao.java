package ru.saransklife.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import ru.saransklife.dao.CacheInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CACHE_INFO.
*/
public class CacheInfoDao extends AbstractDao<CacheInfo, Long> {

    public static final String TABLENAME = "CACHE_INFO";

    /**
     * Properties of entity CacheInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Request = new Property(1, String.class, "request", false, "REQUEST");
        public final static Property Params = new Property(2, String.class, "params", false, "PARAMS");
        public final static Property Last_updated = new Property(3, java.util.Date.class, "last_updated", false, "LAST_UPDATED");
    };


    public CacheInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CacheInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CACHE_INFO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'REQUEST' TEXT," + // 1: request
                "'PARAMS' TEXT," + // 2: params
                "'LAST_UPDATED' INTEGER);"); // 3: last_updated
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CACHE_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CacheInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String request = entity.getRequest();
        if (request != null) {
            stmt.bindString(2, request);
        }
 
        String params = entity.getParams();
        if (params != null) {
            stmt.bindString(3, params);
        }
 
        java.util.Date last_updated = entity.getLast_updated();
        if (last_updated != null) {
            stmt.bindLong(4, last_updated.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CacheInfo readEntity(Cursor cursor, int offset) {
        CacheInfo entity = new CacheInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // request
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // params
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // last_updated
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CacheInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRequest(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setParams(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLast_updated(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CacheInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CacheInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
