package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Region;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.RegionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    public RegionController(RegionRepository regionRepository, CountryRepository countryRepository) {
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
    }

    // Récupérer toutes les régions
    @GetMapping
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    // Récupérer une région par son ID
    @GetMapping("/{id}")
    public Region getById(@PathVariable Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Region create(@RequestBody Region region) {
        Long countryId = region.getCountry().getId();

        // Vérification si l'ID du pays est valide
        if (countryId == null || countryId <= 0) {
            throw new RuntimeException("Invalid country ID");
        }

        // Vérification de l'existence du pays
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country with ID " + countryId + " not found"));

        // Associer le pays à la région
        region.setCountry(country);

        // Enregistrer la région dans la base de données
        return regionRepository.save(region);
    }


    // Supprimer une région
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
