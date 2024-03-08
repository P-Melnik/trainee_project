package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.TrainingTypeService;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class TrainingTypeServiceImpl implements TrainingTypeService {

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Override
    public TrainingType get(String trainingType) {
        return trainingTypeRepo.getTrainingType(trainingType);
    }
    @Override
    public Set<TrainingType> getAll() {
        return trainingTypeRepo.getAll();
    }
}
