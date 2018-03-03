package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import model.Usuario;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class UsuarioDAO extends SQLiteOpenHelper implements DAO<Usuario> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Usuario.class.getSimpleName();
    private static final int VERSION = 1;
    private static UsuarioDAO instance = null;
    private final SQLiteDatabase database;


    private UsuarioDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
        onCreate(database);
    }

    public static UsuarioDAO getInstance(Context context) {
        if (instance == null) {
            instance = new UsuarioDAO(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `Usuario` \n" +
                "(\n" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                " `nome` TEXT NOT NULL,\n" +
                " `idade` INTEGER NOT NULL,\n" +
                " `Login_id` INTEGER NOT NULL \n" +
                " )";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Usuario e) {

    }

    @Override
    public void remove(Usuario e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Usuario> list() {
        return null;
    }

    @Override
    public List<Usuario> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Usuario get(Integer id) {
        return null;
    }

    @Override
    public Usuario getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
