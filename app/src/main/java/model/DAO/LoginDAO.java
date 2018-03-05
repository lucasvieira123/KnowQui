package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Historico;
import model.Login;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class LoginDAO extends SQLiteOpenHelper implements DAO<Login> {
    public static final String NAME_DB = "KnowQui";
    private static final int VERSION = 1;
    private static LoginDAO instance = null;
    private final SQLiteDatabase database;
    private final DataBaseQueryHelper dataBaseQueryHelper;

    private LoginDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Login.class);
        onCreate(database);
    }

    public static LoginDAO getInstance(Context context){
        if(instance == null){
            instance = new LoginDAO(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS `Login` " +
                "(" +
                " `login` TEXT NOT NULL UNIQUE," +
                " `senha` TEXT," +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE " +
                ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Login e) {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Login e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Login> list() {
        return null;
    }

    @Override
    public List<Login> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Login get(Integer id) {
        return null;
    }

    @Override
    public Login getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
