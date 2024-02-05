package trainee.GymApp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainers")
@Data
@NoArgsConstructor
public class Trainer implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Trainer(TrainingType trainingType, User user) {
        this.trainingType = trainingType;
        this.user = user;
    }
}
