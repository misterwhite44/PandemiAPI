package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Test
    public void testFindByNameContaining() {
        Continent continent = new Continent(null, "Europe");
        continentRepository.save(continent);

        Country country1 = new Country(null, "France", "FRA", 67000000L, continent);
        Country country2 = new Country(null, "Germany", "DEU", 83000000L, continent);
        countryRepository.save(country1);
        countryRepository.save(country2);

        Page<Country> result = countryRepository.findByNameContaining("Fr", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(Country::getName).contains("France");
    }
}