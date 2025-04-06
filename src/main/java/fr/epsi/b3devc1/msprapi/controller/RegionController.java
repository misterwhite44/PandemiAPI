package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.Region;
import fr.epsi.b3devc1.msprapi.repository.RegionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Region getById(@PathVariable Integer id) {
        return regionRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Region create(@RequestBody Region region) {
        return regionRepository.save(region);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        regionRepository.deleteById(id);
    }
}
