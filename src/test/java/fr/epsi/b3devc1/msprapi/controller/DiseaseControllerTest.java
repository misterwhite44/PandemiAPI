package fr.epsi.b3devc1.msprapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.repository.DiseaseRepository;
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
public class DiseaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        diseaseRepository.deleteAll();
    }

    @Test
    public void testGetAllDiseases() throws Exception {
        Disease disease = new Disease(null, "COVID-19");
        diseaseRepository.save(disease);

        mockMvc.perform(get("/diseases")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("COVID-19"));
    }

    @Test
    public void testCreateDisease() throws Exception {
        Disease disease = new Disease(null, "COVID-19");

        mockMvc.perform(post("/diseases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(disease)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("COVID-19"));
    }

    @Test
    public void testUpdateDisease() throws Exception {
        Disease disease = new Disease(null, "COVID-19");
        disease = diseaseRepository.save(disease);

        disease.setName("Updated COVID-19");

        mockMvc.perform(put("/diseases/" + disease.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(disease)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated COVID-19"));
    }

    @Test
    public void testDeleteDisease() throws Exception {
        Disease disease = new Disease(null, "COVID-19");
        disease = diseaseRepository.save(disease);

        mockMvc.perform(delete("/diseases/" + disease.getId()))
                .andExpect(status().isNoContent());
    }
}