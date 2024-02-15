package trainee.GymApp;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import trainee.GymApp.config.H2TestConfig;
import trainee.GymApp.config.HibernateConfig;

@ComponentScan(basePackages = "trainee.GymApp")
@Transactional
@EnableTransactionManagement
public class GymAppApplication {

    public static void main(String[] args) {

    }
}
