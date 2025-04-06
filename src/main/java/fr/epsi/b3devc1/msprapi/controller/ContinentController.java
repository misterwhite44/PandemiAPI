package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
public class ContinentController {

    private final ContinentRepository continentRepository;

    public ContinentController(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    @GetMapping
    public List<Continent> getAll() {
        return continentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Continent getById(@PathVariable Integer id) {
        return continentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Continent create(@RequestBody Continent continent) {
        return continentRepository.save(continent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        continentRepository.deleteById(id);
    }
}
