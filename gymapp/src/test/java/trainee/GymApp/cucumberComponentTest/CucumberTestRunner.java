package trainee.GymApp.cucumberComponentTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@ActiveProfiles("test")
@SuppressWarnings("NewClassNamingConvention")
@CucumberOptions(features = "classpath:features/",
        glue = {"trainee.GymApp.cucumberComponentTest.steps"},
        plugin = {"pretty", "html:target/cucumber-reports/component"})
public class CucumberTestRunner {

}
