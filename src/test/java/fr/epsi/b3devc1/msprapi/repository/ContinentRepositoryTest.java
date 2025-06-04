package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Continent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ContinentRepositoryTest {

    @Autowired
    private ContinentRepository continentRepository;

    @Test
    public void testFindByName() {
        Continent continent = new Continent(null, "Europe");
        continentRepository.save(continent);

        Optional<Continent> result = continentRepository.findByName("Europe");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Europe");
    }

    @Test
    public void testFindByNameContaining() {
        Continent continent1 = new Continent(null, "Europe");
        Continent continent2 = new Continent(null, "Eurasia");
        continentRepository.save(continent1);
        continentRepository.save(continent2);

        Page<Continent> result = continentRepository.findByNameContaining("Eu", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(Continent::getName).contains("Europe", "Eurasia");
    }
}