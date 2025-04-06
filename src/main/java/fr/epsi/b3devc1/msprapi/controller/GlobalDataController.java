package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.model.GlobalData;
import fr.epsi.b3devc1.msprapi.repository.GlobalDataRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/globaldata")
public class GlobalDataController {

    private final GlobalDataRepository globalDataRepository;

    public GlobalDataController(GlobalDataRepository globalDataRepository) {
        this.globalDataRepository = globalDataRepository;
    }

    @GetMapping
    public List<GlobalData> getAll() {
        return globalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public GlobalData getById(@PathVariable Integer id) {
        return globalDataRepository.findById(id).orElse(null);
    }

    @PostMapping
    public GlobalData create(@RequestBody GlobalData data) {
        return globalDataRepository.save(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        globalDataRepository.deleteById(id);
    }
}
