package Model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import Model.Nivel;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class NivelDAO extends SQLiteOpenHelper implements DAO<Nivel> {

    public static final String NAME_TABLE = Nivel.class.getSimpleName();
    private static final int VERSION = 1;

    public NivelDAO(Context context) {
        super(context, NAME_TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Nivel e) {

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
    public List<Nivel> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Nivel get(Integer id) {
        return null;
    }

    @Override
    public Nivel getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
