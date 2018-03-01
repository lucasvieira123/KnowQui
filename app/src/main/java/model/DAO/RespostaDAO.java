package model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import model.Resposta;

/**
 * Created by lucas-vieira on 15/02/18.
 */

public class RespostaDAO extends SQLiteOpenHelper implements DAO<Resposta> {
    public static final String NAME_DB = "KnowQui";
    public static final String NAME_TABLE = Resposta.class.getSimpleName();
    private static final int VERSION = 1;
    private static RespostaDAO instance = null;
    private final SQLiteDatabase database;


    private RespostaDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        database = getWritableDatabase();
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
        String CREATE_TABLE = "CREATE TABLE `Resposta` " +
                "( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `descricao` TEXT NOT NULL," +
                " `Pergunta_id` INTEGER NOT NULL," +
                " `EhCorreta` INTEGER NOT NULL )";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void add(Resposta e) {

    }

    @Override
    public void remove(Resposta e) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Resposta> list() {
        return null;
    }

    @Override
    public List<Resposta> list(String selection, String selectionArgs) {
        return null;
    }


    @Override
    public Resposta get(Integer id) {
        return null;
    }

    @Override
    public Resposta getFirst(Integer id) {
        return null;
    }

    @Override
    public Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }


}
