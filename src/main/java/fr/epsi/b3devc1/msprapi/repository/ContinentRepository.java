package fr.epsi.b3devc1.msprapi.repository;

import fr.epsi.b3devc1.msprapi.model.Continent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContinentRepository extends JpaRepository<Continent, Integer> {

    Optional<Continent> findByName(String name);

    Page<Continent> findByNameContaining(String name, Pageable pageable);
}
