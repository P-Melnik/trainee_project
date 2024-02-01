package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;
import trainee.GymApp.service.TrainerService;

import java.util.List;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepo trainerRepo;

    @Override
    public Trainer getById(long id) {
        log.debug("Fetching trainer:" + id);
        return trainerRepo.findById(id);
    }

    @Override
    public void create(TrainerDTO trainerDTO) {
        log.info("Creating trainer: " + trainerDTO);
        User user = new User(0L, trainerDTO.getFirstName(), trainerDTO.getLastName(), UserUtil.generateLogin(trainerDTO.getFirstName(), trainerDTO.getLastName()), UserUtil.generatePassword(), trainerDTO.isActive());
        Trainer trainer = new Trainer(0L, trainerDTO.getSpecialization(), user);
        trainerRepo.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        log.info("Updating trainer: " + trainer);
        trainerRepo.update(trainer);
    }

    public List<Trainer> findAll() {
        log.debug("Fetching all trainers");
        return trainerRepo.findAll();
    }
}
