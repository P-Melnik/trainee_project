package trainee.GymApp;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = "trainee.GymApp")
@Transactional
@EnableTransactionManagement
public class GymAppApplication {

    public static void main(String[] args) {

    }
}
