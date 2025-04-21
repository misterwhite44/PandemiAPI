package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.GlobalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface GlobalDataRepository extends JpaRepository<GlobalData, Integer> {
    Page<GlobalData> findByDate(Date date, Pageable pageable);
    Page<GlobalData> findByCountryId(Long countryId, Pageable pageable);
    Page<GlobalData> findByDiseaseId(Integer diseaseId, Pageable pageable);
}
