package Model.DAO;

import java.util.List;

/**
 * Created by lucas-vieira on 18/02/18.
 */

public interface DAO<Entidy> {

    void add(Entidy e);

    void remove(Entidy e);

    void remove(Integer id);

    List<Entidy> list();

    List<Entidy> list(String whereClause);

    Entidy get(Integer id);

    Entidy getFirst(Integer id);

}
