package trainee.GymApp.cucumberComponentTests.registration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@SuppressWarnings("NewClassNamingConvention")
@CucumberOptions(features = "src/test/java/trainee/GymApp/resources/features/registration",
        glue = {""},
        plugin = {"pretty", "html:target/cucumber-reports/reg_trainee_report.html"})
public class CucumberRegistrationTestRunner {
}
