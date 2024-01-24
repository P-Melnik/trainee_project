package trainee.GymApp.dao;

import java.util.List;

public interface AbstractRepo<T> {

    void create(T t);

    T findById(long id);

    List<T> findAllByType();

}
