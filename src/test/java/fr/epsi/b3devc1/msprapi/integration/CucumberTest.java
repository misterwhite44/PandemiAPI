package fr.epsi.b3devc1.msprapi.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Charge le contexte Spring
@CucumberOptions(
        features = "src/test/resources/features", // Emplacement des fichiers .feature
        glue = "fr.epsi.b3devc1.msprapi.stepdefinitions", // Le package des step definitions
        plugin = {"pretty", "html:target/cucumber-report.html"} // Génère un rapport HTML
)
public class CucumberTest {
}
