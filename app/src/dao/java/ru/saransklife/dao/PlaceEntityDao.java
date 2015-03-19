package ru.saransklife.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import ru.saransklife.dao.PlaceEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLACE_ENTITY.
*/
public class PlaceEntityDao extends AbstractDao<PlaceEntity, Long> {

    public static final String TABLENAME = "PLACE_ENTITY";

    /**
     * Properties of entity PlaceEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Local_id = new Property(0, Long.class, "local_id", true, "LOCAL_ID");
        public final static Property Id = new Property(1, Long.class, "id", false, "ID");
        public final static Property Slug = new Property(2, String.class, "slug", false, "SLUG");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property Phone = new Property(5, String.class, "phone", false, "PHONE");
        public final static Property Email = new Property(6, String.class, "email", false, "EMAIL");
        public final static Property Website = new Property(7, String.class, "website", false, "WEBSITE");
        public final static Property Latitude = new Property(8, Float.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(9, Float.class, "longitude", false, "LONGITUDE");
        public final static Property Description = new Property(10, String.class, "description", false, "DESCRIPTION");
        public final static Property Photo_author = new Property(11, String.class, "photo_author", false, "PHOTO_AUTHOR");
        public final static Property Photo_path = new Property(12, String.class, "photo_path", false, "PHOTO_PATH");
        public final static Property Information = new Property(13, String.class, "information", false, "INFORMATION");
        public final static Property Working_time = new Property(14, String.class, "working_time", false, "WORKING_TIME");
        public final static Property Rating = new Property(15, Float.class, "rating", false, "RATING");
        public final static Property View_count = new Property(16, Integer.class, "view_count", false, "VIEW_COUNT");
        public final static Property Recommended_count = new Property(17, Integer.class, "recommended_count", false, "RECOMMENDED_COUNT");
        public final static Property Event_id = new Property(18, long.class, "event_id", false, "EVENT_ID");
    };

    private Query<PlaceEntity> event_PlacesQuery;

    public PlaceEntityDao(DaoConfig config) {
        super(config);
    }
    
    public PlaceEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLACE_ENTITY' (" + //
                "'LOCAL_ID' INTEGER PRIMARY KEY ," + // 0: local_id
                "'ID' INTEGER," + // 1: id
                "'SLUG' TEXT," + // 2: slug
                "'NAME' TEXT," + // 3: name
                "'ADDRESS' TEXT," + // 4: address
                "'PHONE' TEXT," + // 5: phone
                "'EMAIL' TEXT," + // 6: email
                "'WEBSITE' TEXT," + // 7: website
                "'LATITUDE' REAL," + // 8: latitude
                "'LONGITUDE' REAL," + // 9: longitude
                "'DESCRIPTION' TEXT," + // 10: description
                "'PHOTO_AUTHOR' TEXT," + // 11: photo_author
                "'PHOTO_PATH' TEXT," + // 12: photo_path
                "'INFORMATION' TEXT," + // 13: information
                "'WORKING_TIME' TEXT," + // 14: working_time
                "'RATING' REAL," + // 15: rating
                "'VIEW_COUNT' INTEGER," + // 16: view_count
                "'RECOMMENDED_COUNT' INTEGER," + // 17: recommended_count
                "'EVENT_ID' INTEGER NOT NULL );"); // 18: event_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLACE_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PlaceEntity entity) {
        stmt.clearBindings();
 
        Long local_id = entity.getLocal_id();
        if (local_id != null) {
            stmt.bindLong(1, local_id);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String slug = entity.getSlug();
        if (slug != null) {
            stmt.bindString(3, slug);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(6, phone);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(7, email);
        }
 
        String website = entity.getWebsite();
        if (website != null) {
            stmt.bindString(8, website);
        }
 
        Float latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(9, latitude);
        }
 
        Float longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(10, longitude);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(11, description);
        }
 
        String photo_author = entity.getPhoto_author();
        if (photo_author != null) {
            stmt.bindString(12, photo_author);
        }
 
        String photo_path = entity.getPhoto_path();
        if (photo_path != null) {
            stmt.bindString(13, photo_path);
        }
 
        String information = entity.getInformation();
        if (information != null) {
            stmt.bindString(14, information);
        }
 
        String working_time = entity.getWorking_time();
        if (working_time != null) {
            stmt.bindString(15, working_time);
        }
 
        Float rating = entity.getRating();
        if (rating != null) {
            stmt.bindDouble(16, rating);
        }
 
        Integer view_count = entity.getView_count();
        if (view_count != null) {
            stmt.bindLong(17, view_count);
        }
 
        Integer recommended_count = entity.getRecommended_count();
        if (recommended_count != null) {
            stmt.bindLong(18, recommended_count);
        }
        stmt.bindLong(19, entity.getEvent_id());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PlaceEntity readEntity(Cursor cursor, int offset) {
        PlaceEntity entity = new PlaceEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // local_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // slug
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // phone
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // email
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // website
            cursor.isNull(offset + 8) ? null : cursor.getFloat(offset + 8), // latitude
            cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9), // longitude
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // description
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // photo_author
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // photo_path
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // information
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // working_time
            cursor.isNull(offset + 15) ? null : cursor.getFloat(offset + 15), // rating
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // view_count
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // recommended_count
            cursor.getLong(offset + 18) // event_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PlaceEntity entity, int offset) {
        entity.setLocal_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setSlug(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhone(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEmail(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWebsite(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLatitude(cursor.isNull(offset + 8) ? null : cursor.getFloat(offset + 8));
        entity.setLongitude(cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9));
        entity.setDescription(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPhoto_author(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPhoto_path(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setInformation(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setWorking_time(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setRating(cursor.isNull(offset + 15) ? null : cursor.getFloat(offset + 15));
        entity.setView_count(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setRecommended_count(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setEvent_id(cursor.getLong(offset + 18));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PlaceEntity entity, long rowId) {
        entity.setLocal_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PlaceEntity entity) {
        if(entity != null) {
            return entity.getLocal_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "places" to-many relationship of Event. */
    public List<PlaceEntity> _queryEvent_Places(long event_id) {
        synchronized (this) {
            if (event_PlacesQuery == null) {
                QueryBuilder<PlaceEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Event_id.eq(null));
                event_PlacesQuery = queryBuilder.build();
            }
        }
        Query<PlaceEntity> query = event_PlacesQuery.forCurrentThread();
        query.setParameter(0, event_id);
        return query.list();
    }

}
