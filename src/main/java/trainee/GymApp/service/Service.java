package trainee.GymApp.service;

import trainee.GymApp.entity.Entity;

import java.util.List;

public interface Service<T extends Entity, D> {

    void create(D dto);

    T getById(long id);

    List<T> findAll();

}
