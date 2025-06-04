package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContinentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContinentRepository continentRepository;

    @InjectMocks
    private ContinentController continentController;

    @Test
    public void testGetAllContinents() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(continentController).build();

        Continent continent1 = new Continent(1, "Europe");
        Continent continent2 = new Continent(2, "Asia");
        Page<Continent> mockPage = new PageImpl<>(Arrays.asList(continent1, continent2), PageRequest.of(0, 10), 2);

        when(continentRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        mockMvc.perform(get("/continents?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Europe"))
                .andExpect(jsonPath("$[1].name").value("Asia"));
    }

    @Test
    public void testGetContinentById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(continentController).build();

        when(continentRepository.findById(1)).thenReturn(Optional.of(new Continent(1, "Europe")));

        mockMvc.perform(get("/continents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Europe"));
    }

    @Test
    public void testCreateContinent() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(continentController).build();

        Continent continent = new Continent(null, "Europe");
        when(continentRepository.findByName("Europe")).thenReturn(Optional.empty());
        when(continentRepository.save(any(Continent.class))).thenReturn(new Continent(1, "Europe"));

        mockMvc.perform(post("/continents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Europe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Europe"));
    }

    @Test
    public void testUpdateContinent() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(continentController).build();

        Continent continent = new Continent(1, "Europe");
        when(continentRepository.existsById(1)).thenReturn(true);
        when(continentRepository.save(any(Continent.class))).thenReturn(new Continent(1, "Updated Europe"));

        mockMvc.perform(put("/continents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Europe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Europe"));
    }

    @Test
    public void testDeleteContinent() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(continentController).build();

        when(continentRepository.findById(1)).thenReturn(Optional.of(new Continent(1, "Europe")));

        mockMvc.perform(delete("/continents/1"))
                .andExpect(status().isNoContent());
    }
}