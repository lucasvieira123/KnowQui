package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Historico;
import model.Nivel;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class NivelDAO extends SQLiteOpenHelper implements DAO<Nivel> {
    public static final String NAME_DB = "KnowQui";
    private static final int VERSION = 1;
    private static NivelDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;

    private NivelDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Nivel.class);
        onCreate(database);
    }

    public static NivelDAO getInstance(Context context) {
        if(instance == null){
            instance = new NivelDAO(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS `Nivel` " +
                "( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT NOT NULL )";

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Nivel e) {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Nivel e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Nivel> list() {
        return null;
    }

    @Override
    public List<Nivel> list(String selection, String... selectionArgs) {
        return null;
    }

    @Override
    public Nivel get(String selection, String... selectionArgs) {
        return null;
    }

    @Override
    public Nivel getFirst() {
        return null;
    }


    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
