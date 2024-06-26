package trainee.GymApp.service;

import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.entity.Model;

import java.util.List;

public interface Service<T extends Model, D> {

    CredentialsDTO create(D dto);

    T getById(long id);

    List<T> findAll();

}
