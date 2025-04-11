package fr.epsi.b3devc1.msprapi.service;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ContinentRepository continentRepository;

    @InjectMocks
    private CountryService countryService;

    private Country country;
    private Continent continent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        continent = new Continent(1, "Europe");
        country = new Country(1L, "France", 33, 67000000L, continent); // code3 est Integer, population est Long
    }

    @Test
    void getAllCountries() {
        when(countryRepository.findAll()).thenReturn(List.of(country));

        var countries = countryService.getAllCountries();
        assertFalse(countries.isEmpty());
        assertEquals(1, countries.size());
    }

    @Test
    void getCountryById() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

        Optional<Country> result = countryService.getCountryById(1L);
        assertTrue(result.isPresent());
        assertEquals(country.getName(), result.get().getName());
    }

    @Test
    void createCountry() {
        when(continentRepository.findById((int) anyLong())).thenReturn(Optional.of(continent));
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country createdCountry = countryService.createCountry(country);
        assertEquals(country.getName(), createdCountry.getName());
    }

    @Test
    void deleteCountry() {
        doNothing().when(countryRepository).deleteById(1L);

        countryService.deleteCountry(1L);

        verify(countryRepository, times(1)).deleteById(1L);
    }
}
