package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.dto.RegionRequest;
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
    public Region create(@RequestBody RegionRequest request) {
        // Récupérer le pays
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country with ID " + request.getCountryId() + " not found"));

        // Créer une nouvelle région
        Region region = new Region();
        region.setName(request.getName());
        region.setCountry(country);

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
