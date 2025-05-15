package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.GlobalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import java.util.Date;

public interface GlobalDataRepository extends JpaRepository<GlobalData, Integer> {
    Page<GlobalData> findByDate(Date date, Pageable pageable);
    Page<GlobalData> findByCountryNameContaining(String countryName, Pageable pageable);
    Page<GlobalData> findByDiseaseNameContaining(String diseaseName, Pageable pageable);
    List<GlobalData> findByDate(Date date);
    List<GlobalData> findByCountryNameContaining(String countryName);
    List<GlobalData> findByDiseaseNameContaining(String diseaseName);

}
