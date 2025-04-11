package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.RegionalData;
import fr.epsi.b3devc1.msprapi.repository.RegionalDataRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regionaldata")
public class RegionalDataController {

    private final RegionalDataRepository regionalDataRepository;

    public RegionalDataController(RegionalDataRepository regionalDataRepository) {
        this.regionalDataRepository = regionalDataRepository;
    }

    @GetMapping
    public List<RegionalData> getAll() {
        return regionalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public RegionalData getById(@PathVariable Integer id) {
        return regionalDataRepository.findById(id).orElse(null);
    }

    @PostMapping
    public RegionalData create(@RequestBody RegionalData data) {
        return regionalDataRepository.save(data);
    }

    @PutMapping("/{id}")
    public RegionalData update(@PathVariable Integer id, @RequestBody RegionalData data) {
        // Vérifie si le RegionalData existe
        if (!regionalDataRepository.existsById(id)) {
            return null;  // Retourne null si le RegionalData n'existe pas
        }

        // Met à jour le RegionalData
        data.setId(id);
        return regionalDataRepository.save(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        regionalDataRepository.deleteById(id);
    }
}
