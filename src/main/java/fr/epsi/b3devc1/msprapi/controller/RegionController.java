package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.dto.RegionRequest;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Region;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.RegionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/regions")
@Tag(name = "Régions", description = "Gestion des régions")
public class RegionController {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    public RegionController(RegionRepository regionRepository, CountryRepository countryRepository) {
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les régions avec pagination et filtrage", description = "Permet de récupérer la liste des régions avec des options de pagination et de filtrage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des régions récupérée avec succès")
    })
    public List<Region> getAll(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtrer par nom de région")
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return regionRepository.findByNameContaining(name, pageable).getContent();
        }
        return regionRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une région par ID", description = "Permet de récupérer une région spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Région trouvée"),
            @ApiResponse(responseCode = "404", description = "Région non trouvée")
    })
    public ResponseEntity<Region> getById(
            @Parameter(description = "ID de la région à récupérer", required = true) @PathVariable Long id) {
        Optional<Region> region = regionRepository.findById(id);
        return region.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle région", description = "Permet de créer une nouvelle région.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Région créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création de la région")
    })
    public ResponseEntity<Region> create(
            @Parameter(description = "Détails de la région à créer", required = true) @RequestBody RegionRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country with ID " + request.getCountryId() + " not found"));

        Region region = new Region();
        region.setName(request.getName());
        region.setCountry(country);

        Region createdRegion = regionRepository.save(region);
        return ResponseEntity.status(201).body(createdRegion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une région", description = "Permet de mettre à jour les informations d'une région existante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Région mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Région ou pays non trouvé")
    })
    public ResponseEntity<Region> update(
            @Parameter(description = "ID de la région à mettre à jour", required = true) @PathVariable Long id,
            @Parameter(description = "Détails de la région à mettre à jour", required = true) @RequestBody RegionRequest request) {

        Optional<Region> existingRegion = regionRepository.findById(id);
        if (existingRegion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Region region = existingRegion.get();
        region.setName(request.getName());

        if (request.getCountryId() != null) {
            Country country = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country with ID " + request.getCountryId() + " not found"));
            region.setCountry(country);
        }

        Region updatedRegion = regionRepository.save(region);
        return ResponseEntity.ok(updatedRegion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une région", description = "Permet de supprimer une région en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Région supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Région non trouvée")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la région à supprimer", required = true) @PathVariable Long id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}