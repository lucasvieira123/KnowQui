package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Historico;
import model.Tipo;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class TipoDAO extends SQLiteOpenHelper implements DAO<Tipo>{
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Tipo.class.getSimpleName();
    private static final int VERSION = 1;
    private static TipoDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;

    private TipoDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Tipo.class);
        onCreate(database);
    }

    public static TipoDAO getInstance(Context context) {
        if(instance == null){
            instance = new TipoDAO(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `Tipo` " +
                "( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT NOT NULL )";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Tipo e) {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Tipo e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void removeAll() {
        database.execSQL(dataBaseQueryHelper.getStatementRemoveAll());
    }

    @Override
    public List<Tipo> list() {
        return null;
    }

    @Override
    public List<Tipo> list(String selection, String... selectionArgs) {
        return null;
    }

    @Override
    public Tipo get(String selection, String... selectionArgs) {
        return null;
    }

    @Override
    public Tipo getFirst() {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
