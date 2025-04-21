package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/continents")
@Tag(name = "Continents", description = "Gestion des continents")
public class ContinentController {

    @Autowired
    private ContinentRepository continentRepository;

    @GetMapping
    @Operation(summary = "Récupérer tous les continents", description = "Permet de récupérer la liste de tous les continents disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des continents récupérée avec succès")
    })
    public List<Continent> getAll(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtrer par nom de continent")
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return continentRepository.findByNameContaining(name, pageable).getContent();
        }
        return continentRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un continent par ID", description = "Permet de récupérer un continent spécifique en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Continent trouvé"),
        @ApiResponse(responseCode = "404", description = "Continent non trouvé")
    })
    public ResponseEntity<Continent> getById(
            @Parameter(description = "ID du continent à récupérer", required = true) @PathVariable Integer id) {
        Optional<Continent> continent = continentRepository.findById(id);
        return continent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau continent", description = "Permet de créer un nouveau continent si le nom n'existe pas déjà.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Continent créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Un continent avec ce nom existe déjà")
    })
    public ResponseEntity<Continent> create(
            @Parameter(description = "Détails du continent à créer", required = true) @RequestBody Continent continent) {
        Optional<Continent> existingContinent = continentRepository.findByName(continent.getName());
        if (existingContinent.isPresent()) {
            return ResponseEntity.status(400).body(null);
        }

        Continent savedContinent = continentRepository.save(continent);

        return ResponseEntity.status(201).body(savedContinent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un continent", description = "Permet de mettre à jour les informations d'un continent existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Continent mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Continent non trouvé"),
            @ApiResponse(responseCode = "400", description = "L'ID dans l'URL ne correspond pas à celui du corps de la requête")
    })
    public ResponseEntity<Continent> update(
            @Parameter(description = "ID du continent à mettre à jour", required = true) @PathVariable Integer id,
            @Parameter(description = "Détails du continent à mettre à jour", required = true) @RequestBody Continent continent) {
        if (!continentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        continent.setId(id);
        Continent updatedContinent = continentRepository.save(continent);
        return ResponseEntity.ok(updatedContinent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un continent", description = "Permet de supprimer un continent en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Continent supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Continent non trouvé")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID du continent à supprimer", required = true) @PathVariable Integer id) {
        Optional<Continent> continent = continentRepository.findById(id);
        if (continent.isPresent()) {
            continentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
