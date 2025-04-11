package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private Country country;
    private Continent continent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        continent = new Continent(1, "Europe");
        country = new Country(1L, "France", 33, 67000000L, continent); // code3 est un Integer, population est un Long
    }

    @Test
    void getAllCountries() {
        when(countryService.getAllCountries()).thenReturn(List.of(country));

        var countries = countryController.getAllCountries();
        assertFalse(countries.isEmpty());
        assertEquals(1, countries.size());
    }

    @Test
    void getCountryById() {
        when(countryService.getCountryById(1L)).thenReturn(Optional.of(country));

        ResponseEntity<Country> response = countryController.getCountryById(1L);
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(country.getName(), response.getBody().getName());
    }

    @Test
    void getCountryByIdNotFound() {
        when(countryService.getCountryById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Country> response = countryController.getCountryById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createCountry() {
        when(countryService.createCountry(any(Country.class))).thenReturn(country);

        ResponseEntity<Country> response = countryController.createCountry(country);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(country.getName(), response.getBody().getName());
    }

    @Test
    void deleteCountry() {
        doNothing().when(countryService).deleteCountry(1L);

        ResponseEntity<Void> response = countryController.deleteCountry(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
