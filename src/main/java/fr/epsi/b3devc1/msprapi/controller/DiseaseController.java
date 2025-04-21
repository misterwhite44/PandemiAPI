package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.repository.DiseaseRepository;
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
@RequestMapping("/diseases")
@Tag(name = "Maladies", description = "Gestion des maladies")
public class DiseaseController {

    private final DiseaseRepository diseaseRepository;

    public DiseaseController(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les maladies avec pagination et filtrage", description = "Permet de récupérer la liste des maladies avec des options de pagination et de filtrage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des maladies récupérée avec succès")
    })
    public List<Disease> getAll(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtrer par nom de maladie")
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return diseaseRepository.findByNameContaining(name, pageable).getContent();
        }
        return diseaseRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une maladie par ID", description = "Permet de récupérer une maladie spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maladie trouvée"),
            @ApiResponse(responseCode = "404", description = "Maladie non trouvée")
    })
    public ResponseEntity<Disease> getById(
            @Parameter(description = "ID de la maladie à récupérer", required = true) @PathVariable Integer id) {
        Optional<Disease> disease = diseaseRepository.findById(id);
        return disease.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle maladie", description = "Permet de créer une nouvelle maladie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Maladie créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création de la maladie")
    })
    public ResponseEntity<Disease> create(
            @Parameter(description = "Détails de la maladie à créer", required = true) @RequestBody Disease disease) {
        Disease createdDisease = diseaseRepository.save(disease);
        return ResponseEntity.status(201).body(createdDisease);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une maladie", description = "Permet de mettre à jour les informations d'une maladie existante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maladie mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Maladie non trouvée")
    })
    public ResponseEntity<Disease> update(
            @Parameter(description = "ID de la maladie à mettre à jour", required = true) @PathVariable Integer id,
            @Parameter(description = "Détails de la maladie à mettre à jour", required = true) @RequestBody Disease disease) {
        if (!diseaseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        disease.setId(id);
        Disease updatedDisease = diseaseRepository.save(disease);
        return ResponseEntity.ok(updatedDisease);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une maladie", description = "Permet de supprimer une maladie en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Maladie supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Maladie non trouvée")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la maladie à supprimer", required = true) @PathVariable Integer id) {
        if (!diseaseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        diseaseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}