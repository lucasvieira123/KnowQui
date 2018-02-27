package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Historico;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public abstract class HistoricoDAO extends SQLiteOpenHelper implements DAO<Historico> {

    public static final String NAME_TABLE = Historico.class.getSimpleName();
    private static final int VERSION = 1;

    public HistoricoDAO(Context context) {
        super(context, NAME_TABLE, null, VERSION);
    }

    @Override
    public void add(Historico e) {

    }

    @Override
    public void remove(Historico e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Historico> list() {
        return null;
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
