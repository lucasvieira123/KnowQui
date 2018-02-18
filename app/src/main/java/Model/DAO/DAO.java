package Model.DAO;

import java.util.List;

/**
 * Created by lucas-vieira on 18/02/18.
 */

public interface DAO<entity> {

    void add(entity e);

    void remove(entity e);

    void remove(Integer id);

    List<entity> list();

    List<entity> list(String whereClause);

    entity get(Integer id);

    entity getFirst(Integer id);

}
