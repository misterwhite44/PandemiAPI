package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.model.GlobalData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GlobalDataRepositoryTest {

    @Autowired
    private GlobalDataRepository globalDataRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Test
    public void testFindByDate() {
        GlobalData globalData = new GlobalData(null, new Date(), 1000, 50, 100, 5, 800, 45, 150, 10, 2000L, 300, null, null);
        globalDataRepository.save(globalData);

        Page<GlobalData> result = globalDataRepository.findByDate(globalData.getDate(), PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(GlobalData::getDate).contains(globalData.getDate());
    }

    @Test
    public void testFindByCountryNameContaining() {
        Country country = new Country(null, "France", "FRA", 67000000L, null);
        countryRepository.save(country);

        GlobalData globalData = new GlobalData(null, new Date(), 1000, 50, 100, 5, 800, 45, 150, 10, 2000L, 300, country, null);
        globalDataRepository.save(globalData);

        Page<GlobalData> result = globalDataRepository.findByCountryNameContaining("Fra", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(gd -> gd.getCountry().getName()).contains("France");
    }

    @Test
    public void testFindByDiseaseNameContaining() {
        Disease disease = new Disease(null, "COVID-19");
        diseaseRepository.save(disease);

        GlobalData globalData = new GlobalData(null, new Date(), 1000, 50, 100, 5, 800, 45, 150, 10, 2000L, 300, null, disease);
        globalDataRepository.save(globalData);

        Page<GlobalData> result = globalDataRepository.findByDiseaseNameContaining("COVID", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).extracting(gd -> gd.getDisease().getName()).contains("COVID-19");
    }
}