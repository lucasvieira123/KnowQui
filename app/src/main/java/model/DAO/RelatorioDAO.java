package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import model.Historico;
import model.Relatorio;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 18/06/18.
 */

public class RelatorioDAO  extends SQLiteOpenHelper implements DAO<Relatorio> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Relatorio.class.getSimpleName();
    private static final int VERSION = 1;
    private static RelatorioDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;

    private RelatorioDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Relatorio.class);
        onCreate(database);
    }

    public static RelatorioDAO getInstance(Context context) {
        if (instance == null) {
            instance = new RelatorioDAO(context);

        }
        return instance;
    }

    @Override
    public void add(Relatorio e) throws Exception {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Relatorio e) throws Exception {
        database.execSQL(dataBaseQueryHelper.getStatementDelete(e));
    }

    @Override
    public void remove(Integer id) throws Exception {
        database.execSQL(dataBaseQueryHelper.getStatementDelete(id));
    }

    @Override
    public void removeAll() throws Exception {
        database.execSQL(dataBaseQueryHelper.getStatementRemoveAll());
    }

    @Override
    public List<Relatorio> list() throws Exception {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(),null);
        return (List<Relatorio>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public List<Relatorio> list(String selection, String... selectionArgs) throws Exception {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(selection, selectionArgs),null);
        return (List<Relatorio>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public Relatorio get(String selection, String... selectionArgs) throws Exception {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementElement(selection, selectionArgs),null);
        return (Relatorio) dataBaseQueryHelper.getElement(cursor);
    }

    @Override
    public Relatorio getFirst() throws Exception {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementFirst(),null);
        return (Relatorio) dataBaseQueryHelper.getElement(cursor);
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) throws Exception {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB", "onCreate relatorio");
        String CREATE_TABLE_STATEMENT = "CREATE TABLE  IF NOT EXISTS `Relatorio` " +
                "(" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `tipo` TEXT NOT NULL," +
                " `acertou` INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
