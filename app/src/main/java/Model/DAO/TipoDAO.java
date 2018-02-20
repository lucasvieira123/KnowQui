package Model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import Model.Tipo;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class TipoDAO extends SQLiteOpenHelper implements DAO<Tipo>{

    public static final String NAME_TABLE = Tipo.class.getSimpleName();
    private static final int VERSION = 1;

    public TipoDAO(Context context) {
        super(context, NAME_TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Tipo e) {

    }

    @Override
    public void remove(Tipo e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Tipo> list() {
        return null;
    }

    @Override
    public List<Tipo> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Tipo get(Integer id) {
        return null;
    }

    @Override
    public Tipo getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
