package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.dto.CountryRequest;
import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = "*")
@Tag(name = "Pays", description = "Gestion des pays")
public class CountryController {

    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository, ContinentRepository continentRepository) {
        this.countryRepository = countryRepository;
        this.continentRepository = continentRepository;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les pays avec pagination et filtrage", description = "Permet de récupérer la liste des pays avec des options de pagination et de filtrage.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des pays récupérée avec succès")
    })
    public List<Country> getAllCountries(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtrer par nom de pays")
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return countryRepository.findByNameContaining(name, pageable).getContent();
        }
        return countryRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un pays par ID", description = "Permet de récupérer un pays spécifique en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pays trouvé"),
        @ApiResponse(responseCode = "404", description = "Pays non trouvé")
    })
    public ResponseEntity<Country> getCountryById(
            @Parameter(description = "ID du pays à récupérer", required = true) @PathVariable Long id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau pays", description = "Permet de créer un nouveau pays.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pays créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la création du pays")
    })
    public ResponseEntity<Country> createCountry(
            @Parameter(description = "Détails du pays à créer", required = true) @RequestBody CountryRequest request) {
        Continent continent = continentRepository.findById(request.getContinentId())
                .orElseThrow(() -> new RuntimeException("Continent with ID " + request.getContinentId() + " not found"));

        Country country = new Country();
        country.setName(request.getName());
        country.setCode3(request.getCode3());
        country.setPopulation(request.getPopulation());
        country.setContinent(continent);

        Country createdCountry = countryRepository.save(country);
        return ResponseEntity.status(201).body(createdCountry);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un pays", description = "Permet de mettre à jour les informations d'un pays existant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pays mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Pays non trouvé")
    })
    public ResponseEntity<Country> updateCountry(
            @Parameter(description = "ID du pays à mettre à jour", required = true) @PathVariable Long id,
            @Parameter(description = "Détails du pays à mettre à jour", required = true) @RequestBody CountryRequest request) {
        Optional<Country> existingCountry = countryRepository.findById(id);
        if (existingCountry.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Continent continent = continentRepository.findById(request.getContinentId())
                .orElseThrow(() -> new RuntimeException("Continent with ID " + request.getContinentId() + " not found"));

        Country country = existingCountry.get();
        country.setName(request.getName());
        country.setCode3(request.getCode3());
        country.setPopulation(request.getPopulation());
        country.setContinent(continent);

        Country updatedCountry = countryRepository.save(country);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un pays", description = "Permet de supprimer un pays en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pays supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Pays non trouvé")
    })
    public ResponseEntity<Void> deleteCountry(
            @Parameter(description = "ID du pays à supprimer", required = true) @PathVariable Long id) {

        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
