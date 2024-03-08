package trainee.GymApp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Training implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "trainee_id")
    @EqualsAndHashCode.Include
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @EqualsAndHashCode.Include
    private Trainer trainer;
    @Column(name = "training_name")
    @EqualsAndHashCode.Include
    private String trainingName;
    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
    @Column(name = "training_date")
    private LocalDate trainingDate;
    @Column(name = "training_duration")
    private double trainingDuration;

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, LocalDate trainingDate, double trainingDuration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }
}
