package trainee.GymApp.dao;

import trainee.GymApp.entity.Entity;

import java.util.List;

public interface Repo<T extends Entity> {

    void update(T t);

    void create(T t);

    T findById(long id);

    List<T> findAll();

    void delete(long id);

}
