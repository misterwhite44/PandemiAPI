package fr.epsi.b3devc1.msprapi.service;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    // Mock du repository
    @Mock
    private CountryRepository countryRepository;

    // Service à tester
    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCountries() {
        // Données mockées
        Country country1 = new Country();
        country1.setId(1L);  // Changer Integer à Long ici
        country1.setName("Country1");

        Country country2 = new Country();
        country2.setId(2L);  // Changer Integer à Long ici
        country2.setName("Country2");

        List<Country> countryList = Arrays.asList(country1, country2);

        // Simulation du comportement du repository
        when(countryRepository.findAll()).thenReturn(countryList);

        // Appel du service
        List<Country> countries = countryService.getAllCountries();

        // Vérifications
        assertNotNull(countries, "La liste des pays ne doit pas être nulle");
        assertEquals(2, countries.size(), "La taille de la liste des pays doit être de 2");
        assertEquals("Country1", countries.get(0).getName(), "Le nom du premier pays doit être 'Country1'");
        assertEquals("Country2", countries.get(1).getName(), "Le nom du second pays doit être 'Country2'");

        // Vérification du repository
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCountryById() {
        // Données mockées
        Country country = new Country();
        country.setId(1L);  // Changer Integer à Long ici
        country.setName("Country1");

        // Simulation du comportement du repository
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));  // Changer Integer à Long ici

        // Appel du service
        Optional<Country> foundCountry = countryService.getCountryById(1L);  // Changer Integer à Long ici

        // Vérifications
        assertTrue(foundCountry.isPresent(), "Le pays devrait être trouvé");
        assertEquals("Country1", foundCountry.get().getName(), "Le nom du pays trouvé doit être 'Country1'");

        // Vérification du repository
        verify(countryRepository, times(1)).findById(1L);  // Changer Integer à Long ici
    }

    @Test
    public void testCreateCountry() {
        // Données mockées
        Country country = new Country();
        country.setId(1L);  // Changer Integer à Long ici
        country.setName("Country1");

        // Simulation du comportement du repository
        when(countryRepository.save(country)).thenReturn(country);

        // Appel du service
        Country createdCountry = countryService.createCountry(country);

        // Vérifications
        assertNotNull(createdCountry, "Le pays créé ne doit pas être nul");
        assertEquals("Country1", createdCountry.getName(), "Le nom du pays créé doit être 'Country1'");

        // Vérification du repository
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    public void testDeleteCountry() {
        Long countryId = 1L;  // Changer Integer à Long ici

        // Appel du service
        countryService.deleteCountry(countryId);

        // Vérification du repository
        verify(countryRepository, times(1)).deleteById(countryId);  // Changer Integer à Long ici
    }
}
