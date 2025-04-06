package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Country getById(@PathVariable Integer id) {
        return countryRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Country create(@RequestBody Country country) {
        return countryRepository.save(country);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        countryRepository.deleteById(id);
    }
}
