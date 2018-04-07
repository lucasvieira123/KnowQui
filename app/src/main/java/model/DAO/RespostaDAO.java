package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Resposta;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class RespostaDAO extends SQLiteOpenHelper implements DAO<Resposta> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Resposta.class.getSimpleName();
    private static final int VERSION = 1;
    private static RespostaDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;


    private RespostaDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Resposta.class);
        onCreate(database);
    }

    public static RespostaDAO getInstance(Context context) {
        if(instance == null){
            instance = new RespostaDAO(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `Resposta` " +
                "( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT NOT NULL," +
                " `letra` TEXT NOT NULL," +
                " `id_pergunta` INTEGER NOT NULL," +
                " `EhCorreta` INTEGER NOT NULL )";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Resposta e) {

        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Resposta e) {
        database.execSQL(dataBaseQueryHelper.getStatementDelete(e));
    }

    @Override
    public void remove(Integer id) {
        database.execSQL(dataBaseQueryHelper.getStatementDelete(id));
    }

    @Override
    public void removeAll() {
        database.execSQL(dataBaseQueryHelper.getStatementRemoveAll());
    }

    @Override
    public List<Resposta> list() {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(),null);
        return (List<Resposta>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public List<Resposta> list(String selection, String... selectionArgs) {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(selection, selectionArgs),null);
        return (List<Resposta>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public Resposta get(String selection, String... selectionArgs) {

        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementElement(selection, selectionArgs),null);
        return (Resposta) dataBaseQueryHelper.getElement(cursor);
    }

    @Override
    public Resposta getFirst() {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementFirst(),null);
        return (Resposta) dataBaseQueryHelper.getElement(cursor);
    }



    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
