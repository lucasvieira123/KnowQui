package model.DAO;

import android.database.Cursor;

import java.util.List;

/**
 * Created by lucas-vieira on 18/02/18.
 */

public interface DAO<entity> {
    /*
    distinct                                  // Distinct
    [] columns                                // Collumns retorned
    projection,                               // The columns to return
    selection,                                // The columns for the WHERE clause
    selectionArgs                             // The values for the WHERE clause
    groupBy                                   // Group the rows
    having                                    // Filter by row groups
    orderBy                                   // The sort order
    limit                                     // Limit
    */

    void add(entity e);

    void remove(entity e);

    void remove(Integer id);

    List<entity> list();

    List<entity> list(String selection, String selectionArgs);

    entity get(Integer id);

    entity getFirst(Integer id);

    Cursor getCursor(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);

}
