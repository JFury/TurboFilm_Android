package tv.turbik.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import tv.turbik.dao.Series;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SERIES.
*/
public class SeriesDao extends AbstractDao<Series, Long> {

    public static final String TABLENAME = "SERIES";

    /**
     * Properties of entity Series.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Alias = new Property(1, String.class, "alias", false, "ALIAS");
        public final static Property NameEn = new Property(2, String.class, "nameEn", false, "NAME_EN");
        public final static Property NameRu = new Property(3, String.class, "nameRu", false, "NAME_RU");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
    };

    private DaoSession daoSession;


    public SeriesDao(DaoConfig config) {
        super(config);
    }
    
    public SeriesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SERIES' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ALIAS' TEXT," + // 1: alias
                "'NAME_EN' TEXT," + // 2: nameEn
                "'NAME_RU' TEXT," + // 3: nameRu
                "'DESCRIPTION' TEXT);"); // 4: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SERIES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Series entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String alias = entity.getAlias();
        if (alias != null) {
            stmt.bindString(2, alias);
        }
 
        String nameEn = entity.getNameEn();
        if (nameEn != null) {
            stmt.bindString(3, nameEn);
        }
 
        String nameRu = entity.getNameRu();
        if (nameRu != null) {
            stmt.bindString(4, nameRu);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
    }

    @Override
    protected void attachEntity(Series entity) {
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
    public Series readEntity(Cursor cursor, int offset) {
        Series entity = new Series( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // alias
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nameEn
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nameRu
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // description
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Series entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAlias(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNameEn(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNameRu(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Series entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Series entity) {
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
