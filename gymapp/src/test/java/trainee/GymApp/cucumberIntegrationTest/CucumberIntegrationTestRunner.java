package trainee.GymApp.cucumberIntegrationTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@SuppressWarnings("NewClassNamingConvention")
@ActiveProfiles("testreport")
@CucumberOptions(features = "classpath:features/integration_features",
        glue = {"trainee.GymApp.cucumberIntegrationTest.steps"},
        plugin = {"pretty", "html:target/cucumber-reports/integration"})
public class CucumberIntegrationTestRunner {
}
