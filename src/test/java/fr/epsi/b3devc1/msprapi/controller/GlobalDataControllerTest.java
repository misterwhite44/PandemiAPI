package fr.epsi.b3devc1.msprapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epsi.b3devc1.msprapi.dto.GlobalDataRequest;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.model.GlobalData;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.DiseaseRepository;
import fr.epsi.b3devc1.msprapi.repository.GlobalDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GlobalDataRepository globalDataRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        globalDataRepository.deleteAll();
        countryRepository.deleteAll();
        diseaseRepository.deleteAll();
    }

    @Test
    public void testGetAllGlobalData() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Disease disease = new Disease(null, "COVID-19");
        diseaseRepository.save(disease);

        GlobalData globalData = new GlobalData(null, new Date(), 1000, 50, 100, 5, 800, 45, 150, 10, 2000L, 300, country, disease);
        globalDataRepository.save(globalData);

        mockMvc.perform(get("/globaldata")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country.name").value("France"))
                .andExpect(jsonPath("$[0].disease.name").value("COVID-19"));
    }

    @Test
    public void testCreateGlobalData() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Disease disease = new Disease(null, "COVID-19");
        diseaseRepository.save(disease);

        GlobalDataRequest request = new GlobalDataRequest();
        request.setDate(new Date());
        request.setTotalCases(1000);
        request.setNewCases(50);
        request.setTotalDeaths(100);
        request.setNewDeaths(5);
        request.setTotalRecovered(800);
        request.setNewRecovered(45);
        request.setActiveCases(150);
        request.setSeriousCritical(10);
        request.setTotalTests(2000L);
        request.setTestsPerMillion(300);
        request.setCountryId(country.getId().longValue());
        request.setDiseaseId(disease.getId());

        mockMvc.perform(post("/globaldata")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country.name").value("France"))
                .andExpect(jsonPath("$.disease.name").value("COVID-19"));
    }
}