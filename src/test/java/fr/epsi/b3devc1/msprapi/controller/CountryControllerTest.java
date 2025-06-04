package fr.epsi.b3devc1.msprapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epsi.b3devc1.msprapi.dto.CountryRequest;
import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        countryRepository.deleteAll();
        continentRepository.deleteAll();
    }

    @Test
    public void testGetAllCountries() throws Exception {
        Continent continent = new Continent(null, "Europe");
        continentRepository.save(continent);

        Country country = new Country(null, "France", "FRA", 67000000L, continent);
        countryRepository.save(country);

        mockMvc.perform(get("/countries")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("France"));
    }

    @Test
    public void testCreateCountry() throws Exception {
        Continent continent = new Continent(null, "Europe");
        continentRepository.save(continent);

        CountryRequest request = new CountryRequest();
        request.setName("France");
        request.setCode3("FRA");
        request.setPopulation(67000000L);
        request.setContinentId(continent.getId().intValue());

        mockMvc.perform(post("/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("France"));
    }
}