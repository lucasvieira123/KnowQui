package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Historico;
import model.Pergunta;
import utils.DataBaseQueryHelper;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class PerguntaDAO extends SQLiteOpenHelper implements DAO<Pergunta> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Pergunta.class.getSimpleName();
    private static final int VERSION = 1;
    private static PerguntaDAO instance = null;
    private final SQLiteDatabase database;
    private DataBaseQueryHelper dataBaseQueryHelper;

    private PerguntaDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        dataBaseQueryHelper = new DataBaseQueryHelper(Pergunta.class);
        onCreate(database);
    }

    public static PerguntaDAO getInstance(Context context) {

        if(instance == null){
            instance = new PerguntaDAO(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS `Pergunta` " +
                "( " +
                "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT NOT NULL," +
                " `complemento` TEXT NOT NULL," +
                " `imagem` TEXT ," +
                " `diretorioImagem` TEXT ," +
                " `nivel` TEXT NOT NULL," +
                " `tipo` TEXT NOT NULL," +
                "'tempo' INTEGER NOT NULL)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Pergunta e) {
        database.execSQL(dataBaseQueryHelper.getStatementInsert(e));
    }

    @Override
    public void remove(Pergunta e) {
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
    public List<Pergunta> list() {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(),null);
        return (List<Pergunta>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public List<Pergunta> list(String selection, String... selectionArgs) {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementList(selection, selectionArgs),null);
        return (List<Pergunta>) (List<?>) dataBaseQueryHelper.getList(cursor);
    }

    @Override
    public Pergunta get(String selection, String... selectionArgs) {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementElement(selection, selectionArgs),null);
        return (Pergunta) dataBaseQueryHelper.getElement(cursor);
    }

    @Override
    public Pergunta getFirst() {
        Cursor cursor = database.rawQuery(dataBaseQueryHelper.getStatementFirst(),null);
        return (Pergunta) dataBaseQueryHelper.getElement(cursor);
    }



    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
