package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Disease;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DiseaseRepositoryTest {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Test
    public void testFindByNameContaining() {
        Disease disease1 = new Disease(null, "COVID-19");
        Disease disease2 = new Disease(null, "Influenza");
        diseaseRepository.save(disease1);
        diseaseRepository.save(disease2);

        Page<Disease> result = diseaseRepository.findByNameContaining("COVID", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(Disease::getName).contains("COVID-19");
    }
}