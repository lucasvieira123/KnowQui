package Model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import Model.Pergunta;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class PerguntaDAO extends SQLiteOpenHelper implements DAO<Pergunta> {

    public static final String NAME_TABLE = Pergunta.class.getSimpleName();
    private static final int VERSION = 1;

    public PerguntaDAO(Context context) {
        super(context, NAME_TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Pergunta e) {

    }

    @Override
    public void remove(Pergunta e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Pergunta> list() {
        return null;
    }

    @Override
    public List<Pergunta> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Pergunta get(Integer id) {
        return null;
    }

    @Override
    public Pergunta getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
