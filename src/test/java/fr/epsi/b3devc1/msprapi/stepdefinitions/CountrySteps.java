package fr.epsi.b3devc1.msprapi.stepdefinitions;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.service.CountryService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

@Component // Ajoute cette annotation pour que Spring gère cette classe
public class CountrySteps {

    @Autowired
    private CountryService countryService;

    private Country country;

    @Given("un pays {string} avec le code3 {int} et une population de {long}")
    public void givenCountryWithDetails(String name, Integer code3, Long population) {
        country = new Country();
        country.setName(name);
        country.setCode3(code3);
        country.setPopulation(population);
    }

    @When("je crée le pays")
    public void createCountry() {
        countryService.createCountry(country);
    }

    @Then("le pays {string} doit être créé avec le code3 {int} et une population de {long}")
    public void countryShouldBeCreated(String name, Integer code3, Long population) {
        Country createdCountry = countryService.getCountryById(country.getId()).orElse(null);
        assertNotNull(createdCountry);
        assertEquals(name, createdCountry.getName());
        assertEquals(code3, createdCountry.getCode3());
        assertEquals(population, createdCountry.getPopulation());
    }

    @Given("un pays {string} existe avec l'ID {long}")
    public void givenCountryExists(String name, Long id) {
        country = new Country(id, name, 33, 67000000L, null);
    }

    @When("je récupère le pays avec l'ID {long}")
    public void retrieveCountryById(Long id) {
        country = countryService.getCountryById(id).orElse(null);
    }

    @Then("le pays récupéré doit être {string} avec le code3 {int}")
    public void countryShouldBeRetrieved(String name, Integer code3) {
        assertNotNull(country);
        assertEquals(name, country.getName());
        assertEquals(code3, country.getCode3());
    }

    @When("je supprime le pays avec l'ID {long}")
    public void deleteCountry(Long id) {
        countryService.deleteCountry(id);
    }

    @Then("le pays avec l'ID {long} ne doit plus exister")
    public void countryShouldNotExist(Long id) {
        assertTrue(countryService.getCountryById(id).isEmpty());
    }
}
