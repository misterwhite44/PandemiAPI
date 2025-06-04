package fr.epsi.b3devc1.msprapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epsi.b3devc1.msprapi.dto.RegionRequest;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Region;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.GlobalDataRepository;
import fr.epsi.b3devc1.msprapi.repository.RegionRepository;
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
public class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GlobalDataRepository globalDataRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        globalDataRepository.deleteAll();
        regionRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Test
    public void testGetAllRegions() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Region region = new Region(null, "Île-de-France", 12000000L, country);
        regionRepository.save(region);

        mockMvc.perform(get("/regions")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Île-de-France"));
    }

    @Test
    public void testCreateRegion() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        RegionRequest request = new RegionRequest();
        request.setName("Île-de-France");
        request.setPopulation(12000000L);
        request.setCountryId(country.getId());

        mockMvc.perform(post("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Île-de-France"));
    }

    @Test
    public void testUpdateRegion() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Region region = new Region(null, "Île-de-France", 12000000L, country);
        region = regionRepository.save(region);

        RegionRequest request = new RegionRequest();
        request.setName("Updated Île-de-France");
        request.setPopulation(13000000L);
        request.setCountryId(country.getId());

        mockMvc.perform(put("/regions/" + region.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Île-de-France"));
    }

    @Test
    public void testDeleteRegion() throws Exception {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Region region = new Region(null, "Île-de-France", 12000000L, country);
        region = regionRepository.save(region);

        mockMvc.perform(delete("/regions/" + region.getId()))
                .andExpect(status().isNoContent());
    }
}