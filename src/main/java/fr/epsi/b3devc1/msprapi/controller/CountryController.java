package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import fr.epsi.b3devc1.msprapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;
    private final ContinentRepository continentRepository; // Ajoute l'injection de ContinentRepository

    @Autowired
    public CountryController(CountryService countryService, ContinentRepository continentRepository) {
        this.countryService = countryService;
        this.continentRepository = continentRepository; // Injecte le repository ici
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Optional<Country> country = countryService.getCountryById(id);
        return country.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        // Assurez-vous que le continent est enregistré avant de créer le pays
        if (country.getContinent() != null && country.getContinent().getId() == null) {
            Continent savedContinent = continentRepository.save(country.getContinent());
            country.setContinent(savedContinent);
        }

        Country createdCountry = countryService.createCountry(country);
        return ResponseEntity.status(201).body(createdCountry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
