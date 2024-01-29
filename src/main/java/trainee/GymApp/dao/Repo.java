package trainee.GymApp.dao;

import trainee.GymApp.entity.Identifiable;

import java.util.List;

public interface Repo<T extends Identifiable> {

    void update(T t);

    void create(T t);

    T findById(long id);

    List<T> findAll();

    void delete(long id);

}
