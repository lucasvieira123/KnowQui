package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

import model.Historico;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class HistoricoDAO extends SQLiteOpenHelper implements DAO<Historico> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Historico.class.getSimpleName();
    private static final int VERSION = 1;
    private static HistoricoDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;


    private HistoricoDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Historico.class);
        onCreate(database);
    }

    public static HistoricoDAO getInstance(Context context) {
        if (instance == null) {
            instance = new HistoricoDAO(context);

        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB", "onCreate historico");
        String CREATE_TABLE_STATEMENT = "CREATE TABLE  IF NOT EXISTS `Historico` " +
                "(" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT," +
                " `Usuario_id` INTEGER NOT NULL," +
                " `acertou` INTEGER NOT NULL," +
                " `data` TEXT NOT NULL " +
                ")";
        db.execSQL(CREATE_TABLE_STATEMENT);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Historico e) {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Historico e) {
        database.execSQL(dataBaseQueryHelper.getStatementDelete(e));

    }

    @Override
    public void remove(Integer id) {

        database.execSQL(dataBaseQueryHelper.getStatementDelete(id));
    }

    @Override
    public List<Historico> list() {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(),null);
        return (List<Historico>) (List<?>) dataBaseQueryHelper.getList(cursor);

    }

    @Override
    public List<Historico> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Historico get(Integer id) {
        return null;
    }

    @Override
    public Historico getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }

}
