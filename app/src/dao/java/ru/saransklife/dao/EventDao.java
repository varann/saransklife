package ru.saransklife.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import ru.saransklife.dao.Event;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EVENT.
*/
public class EventDao extends AbstractDao<Event, Long> {

    public static final String TABLENAME = "EVENT";

    /**
     * Properties of entity Event.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Local_id = new Property(0, Long.class, "local_id", true, "LOCAL_ID");
        public final static Property Id = new Property(1, Long.class, "id", false, "ID");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property Story = new Property(5, String.class, "story", false, "STORY");
        public final static Property Start_date = new Property(6, java.util.Date.class, "start_date", false, "START_DATE");
        public final static Property End_date = new Property(7, java.util.Date.class, "end_date", false, "END_DATE");
        public final static Property Time_type = new Property(8, String.class, "time_type", false, "TIME_TYPE");
        public final static Property Is_repeatable = new Property(9, Boolean.class, "is_repeatable", false, "IS_REPEATABLE");
        public final static Property Photo_author = new Property(10, String.class, "photo_author", false, "PHOTO_AUTHOR");
        public final static Property Photo_path = new Property(11, String.class, "photo_path", false, "PHOTO_PATH");
        public final static Property Price = new Property(12, String.class, "price", false, "PRICE");
        public final static Property Category_id = new Property(13, Long.class, "category_id", false, "CATEGORY_ID");
    };

    private DaoSession daoSession;


    public EventDao(DaoConfig config) {
        super(config);
    }
    
    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENT' (" + //
                "'LOCAL_ID' INTEGER PRIMARY KEY ," + // 0: local_id
                "'ID' INTEGER," + // 1: id
                "'TYPE' TEXT," + // 2: type
                "'NAME' TEXT," + // 3: name
                "'DESCRIPTION' TEXT," + // 4: description
                "'STORY' TEXT," + // 5: story
                "'START_DATE' INTEGER," + // 6: start_date
                "'END_DATE' INTEGER," + // 7: end_date
                "'TIME_TYPE' TEXT," + // 8: time_type
                "'IS_REPEATABLE' INTEGER," + // 9: is_repeatable
                "'PHOTO_AUTHOR' TEXT," + // 10: photo_author
                "'PHOTO_PATH' TEXT," + // 11: photo_path
                "'PRICE' TEXT," + // 12: price
                "'CATEGORY_ID' INTEGER);"); // 13: category_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Event entity) {
        stmt.clearBindings();
 
        Long local_id = entity.getLocal_id();
        if (local_id != null) {
            stmt.bindLong(1, local_id);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        String story = entity.getStory();
        if (story != null) {
            stmt.bindString(6, story);
        }
 
        java.util.Date start_date = entity.getStart_date();
        if (start_date != null) {
            stmt.bindLong(7, start_date.getTime());
        }
 
        java.util.Date end_date = entity.getEnd_date();
        if (end_date != null) {
            stmt.bindLong(8, end_date.getTime());
        }
 
        String time_type = entity.getTime_type();
        if (time_type != null) {
            stmt.bindString(9, time_type);
        }
 
        Boolean is_repeatable = entity.getIs_repeatable();
        if (is_repeatable != null) {
            stmt.bindLong(10, is_repeatable ? 1l: 0l);
        }
 
        String photo_author = entity.getPhoto_author();
        if (photo_author != null) {
            stmt.bindString(11, photo_author);
        }
 
        String photo_path = entity.getPhoto_path();
        if (photo_path != null) {
            stmt.bindString(12, photo_path);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(13, price);
        }
 
        Long category_id = entity.getCategory_id();
        if (category_id != null) {
            stmt.bindLong(14, category_id);
        }
    }

    @Override
    protected void attachEntity(Event entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Event readEntity(Cursor cursor, int offset) {
        Event entity = new Event( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // local_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // story
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // start_date
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // end_date
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // time_type
            cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0, // is_repeatable
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // photo_author
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // photo_path
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // price
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13) // category_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Event entity, int offset) {
        entity.setLocal_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStory(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStart_date(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setEnd_date(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setTime_type(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIs_repeatable(cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0);
        entity.setPhoto_author(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPhoto_path(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPrice(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCategory_id(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Event entity, long rowId) {
        entity.setLocal_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Event entity) {
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getEventCategoryDao().getAllColumns());
            builder.append(" FROM EVENT T");
            builder.append(" LEFT JOIN EVENT_CATEGORY T0 ON T.'CATEGORY_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Event loadCurrentDeep(Cursor cursor, boolean lock) {
        Event entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        EventCategory eventCategory = loadCurrentOther(daoSession.getEventCategoryDao(), cursor, offset);
        entity.setEventCategory(eventCategory);

        return entity;    
    }

    public Event loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Event> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Event> list = new ArrayList<Event>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Event> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Event> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
