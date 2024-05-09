package trainee.service.summary.cucumberComponentTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@ActiveProfiles("test")
@CucumberOptions(features = "classpath:features/",
        glue = {"trainee.service.summery.cucumberComponentTest.steps"},
        plugin = {"pretty", "html:target/cucumber-reports/summary_service"})
public class CucumberTestRunner {
}
