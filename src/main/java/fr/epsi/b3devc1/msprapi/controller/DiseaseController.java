package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.repository.DiseaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseRepository diseaseRepository;

    public DiseaseController(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    @GetMapping
    public List<Disease> getAll() {
        return diseaseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Disease getById(@PathVariable Integer id) {
        return diseaseRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Disease create(@RequestBody Disease disease) {
        return diseaseRepository.save(disease);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        diseaseRepository.deleteById(id);
    }
}
