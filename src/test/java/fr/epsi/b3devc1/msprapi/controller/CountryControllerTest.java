package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryController countryController;

    private Country country;

    @BeforeEach
    public void setUp() {
        country = new Country();
        country.setId(1L);  // Change ici pour Long
        country.setName("Country1");
        country.setIso2("C1");
        country.setIso3("C1C");
        country.setCode3(1);
        country.setPopulation(1000000L);
        country.setWhoRegion("Region1");
    }

    @Test
    public void testGetAllCountries() throws Exception {
        // Données mockées
        Country country2 = new Country();
        country2.setId(2L);  // Change ici pour Long
        country2.setName("Country2");

        when(countryRepository.findAll()).thenReturn(Arrays.asList(country, country2));

        // Test GET /api/countries
        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Country1"))
                .andExpect(jsonPath("$[1].name").value("Country2"));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCountryById() throws Exception {
        // Données mockées
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));  // Change ici pour Long

        // Test GET /api/countries/{id}
        mockMvc.perform(get("/api/countries/{id}", 1L))  // Change ici pour Long
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Country1"))
                .andExpect(jsonPath("$.iso2").value("C1"));

        verify(countryRepository, times(1)).findById(1L);  // Change ici pour Long
    }

    @Test
    public void testCreateCountry() throws Exception {
        // Données mockées
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        // Test POST /api/countries
        mockMvc.perform(post("/api/countries")
                        .contentType("application/json")
                        .content("{\"name\":\"Country1\",\"iso2\":\"C1\",\"iso3\":\"C1C\",\"code3\":1,\"population\":1000000,\"whoRegion\":\"Region1\"}"))
                .andExpect(status().isCreated())  // Code de statut 201 pour création
                .andExpect(jsonPath("$.name").value("Country1"));

        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    public void testDeleteCountry() throws Exception {
        // Données mockées
        doNothing().when(countryRepository).deleteById(1L);  // Change ici pour Long

        // Test DELETE /api/countries/{id}
        mockMvc.perform(delete("/api/countries/{id}", 1L))  // Change ici pour Long
                .andExpect(status().isOk());

        verify(countryRepository, times(1)).deleteById(1L);  // Change ici pour Long
    }
}
