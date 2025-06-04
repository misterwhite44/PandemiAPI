package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testFindByNameContaining() {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        Region region1 = new Region(null, "Île-de-France", 12000000L, country);
        Region region2 = new Region(null, "Provence-Alpes-Côte d'Azur", 5000000L, country);
        regionRepository.save(region1);
        regionRepository.save(region2);

        Page<Region> result = regionRepository.findByNameContaining("Île", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(Region::getName).contains("Île-de-France");
    }
}