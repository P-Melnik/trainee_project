package trainee.GymApp.dao;

import trainee.GymApp.entity.Model;

import java.util.List;

public interface Repo<T extends Model> {

    void update(T t);

    void create(T t);

    T findById(long id);

    List<T> findAll();

    boolean delete(long id);

}
