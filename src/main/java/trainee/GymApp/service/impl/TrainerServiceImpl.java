package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.service.Generators;
import trainee.GymApp.service.TrainerService;

import java.util.List;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepo trainerRepo;


    @Override
    public Trainer getTrainerById(long id) {
        log.debug("Fetching trainer:" + id);
        return trainerRepo.findById(id);
    }

    @Override
    public void createTrainer(TrainerDTO trainerDTO) {
        log.info("Creating trainer: " + trainerDTO);
        Trainer trainer = new Trainer();
        trainer.setId(Generators.generateTrainerId());
        trainer.setActive(trainerDTO.isActive());
        trainer.setFirstName(trainerDTO.getFirstName());
        trainer.setLastName(trainerDTO.getLastName());
        trainer.setUserId(Generators.generateUserId());
        trainer.setSpecialization(trainerDTO.getSpecialization());
        trainer.setPassword(Generators.generatePassword());
        trainer.setUserName(Generators.generateUserName(trainer.getFirstName(),
                trainer.getLastName()));
        trainerRepo.create(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        log.info("Updating trainer: " + trainer);
        trainerRepo.updateTrainer(trainer);
    }

    public List<Trainer> findAll() {
        log.debug("Fetching all trainers");
        return trainerRepo.findAllByType();
    }
}
