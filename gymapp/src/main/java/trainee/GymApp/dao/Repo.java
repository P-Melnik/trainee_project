package trainee.GymApp.dao;

import trainee.GymApp.entity.Model;

import java.util.List;
import java.util.Optional;

public interface Repo<T extends Model> {

    Optional<T> update(T t);

    void create(T t);

    T findById(long id);

    List<T> findAll();

}
