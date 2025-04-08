package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/continents")
public class ContinentController {

    @Autowired
    private ContinentRepository continentRepository;

    // Récupère tous les continents
    @GetMapping
    public List<Continent> getAll() {
        return continentRepository.findAll();
    }

    // Récupère un continent par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Continent> getById(@PathVariable Integer id) {
        Optional<Continent> continent = continentRepository.findById(id);
        return continent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée un nouveau continent (POST)
    @PostMapping
    public ResponseEntity<Continent> create(@RequestBody Continent continent) {
        // Vérifie si un continent avec ce nom existe déjà
        Optional<Continent> existingContinent = continentRepository.findByName(continent.getName());
        if (existingContinent.isPresent()) {
            // Si un continent avec ce nom existe déjà, renvoie une erreur 400
            return ResponseEntity.status(400).body(null);
        }

        // Sauvegarde le continent dans la base de données (id sera généré automatiquement)
        Continent savedContinent = continentRepository.save(continent);

        // Renvoie le continent créé avec un statut 201 (Created)
        return ResponseEntity.status(201).body(savedContinent);
    }

    // Supprime un continent par son ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Continent> continent = continentRepository.findById(id);
        if (continent.isPresent()) {
            continentRepository.deleteById(id);
            return ResponseEntity.noContent().build();  // Retourne un statut 204 No Content
        }
        return ResponseEntity.notFound().build();  // Retourne un statut 404 Not Found si le continent n'existe pas
    }
}
