package fr.epsi.b3devc1.msprapi.controller;

import fr.epsi.b3devc1.msprapi.dto.GlobalDataRequest;
import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Disease;
import fr.epsi.b3devc1.msprapi.model.GlobalData;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.DiseaseRepository;
import fr.epsi.b3devc1.msprapi.repository.GlobalDataRepository;
import fr.epsi.b3devc1.msprapi.repository.RegionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/globaldata")
@CrossOrigin(origins = "*")
@Tag(name = "Données Globales", description = "Gestion des données globales")
public class GlobalDataController {

    private final GlobalDataRepository globalDataRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final DiseaseRepository diseaseRepository;

    public GlobalDataController(GlobalDataRepository globalDataRepository, CountryRepository countryRepository, RegionRepository regionRepository, DiseaseRepository diseaseRepository) {
        this.globalDataRepository = globalDataRepository;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
        this.diseaseRepository = diseaseRepository;
    }

    @GetMapping
    @Operation(summary = "Récupérer les 200 premières données globales avec filtres optionnels", description = "Permet de récupérer jusqu'à 200 données globales avec des options de filtrage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des données globales récupérée avec succès")
    })
    public List<GlobalData> getAll(
            @Parameter(description = "Filtrer par date (format : dd-MM-yyyy)")
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
            @Parameter(description = "Filtrer par nom de pays")
            @RequestParam(required = false) String countryName,
            @Parameter(description = "Filtrer par nom de maladie")
            @RequestParam(required = false) String diseaseName) {

        Pageable pageable = PageRequest.of(0, 200);

        if (date != null) {
            return globalDataRepository.findByDate(date, pageable).getContent();
        } else if (countryName != null && !countryName.isEmpty()) {
            return globalDataRepository.findByCountryNameContaining(countryName, pageable).getContent();
        } else if (diseaseName != null && !diseaseName.isEmpty()) {
            return globalDataRepository.findByDiseaseNameContaining(diseaseName, pageable).getContent();
        }

        return globalDataRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une donnée globale par ID", description = "Permet de récupérer une donnée globale spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donnée globale trouvée"),
            @ApiResponse(responseCode = "404", description = "Donnée globale non trouvée")
    })
    public ResponseEntity<GlobalData> getById(@PathVariable Integer id) {
        Optional<GlobalData> globalData = globalDataRepository.findById(id);
        return globalData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle donnée globale", description = "Permet de créer une nouvelle donnée globale.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Donnée globale créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création de la donnée globale")
    })
    public ResponseEntity<GlobalData> create(@RequestBody GlobalDataRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country with ID " + request.getCountryId() + " not found"));
        Disease disease = diseaseRepository.findById(request.getDiseaseId())
                .orElseThrow(() -> new RuntimeException("Disease with ID " + request.getDiseaseId() + " not found"));

        GlobalData globalData = new GlobalData();
        globalData.setDate(request.getDate());
        globalData.setTotalCases(request.getTotalCases());
        globalData.setNewCases(request.getNewCases());
        globalData.setTotalDeaths(request.getTotalDeaths());
        globalData.setNewDeaths(request.getNewDeaths());
        globalData.setTotalRecovered(request.getTotalRecovered());
        globalData.setNewRecovered(request.getNewRecovered());
        globalData.setActiveCases(request.getActiveCases());
        globalData.setSeriousCritical(request.getSeriousCritical());
        globalData.setTotalTests(request.getTotalTests());
        globalData.setTestsPerMillion(request.getTestsPerMillion());
        globalData.setCountry(country);
        globalData.setDisease(disease);

        GlobalData createdGlobalData = globalDataRepository.save(globalData);
        return ResponseEntity.status(201).body(createdGlobalData);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une donnée globale", description = "Permet de mettre à jour les informations d'une donnée globale existante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donnée globale mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Donnée globale non trouvée")
    })
    public ResponseEntity<GlobalData> update(@PathVariable Integer id, @RequestBody GlobalDataRequest request) {
        Optional<GlobalData> existingGlobalData = globalDataRepository.findById(id);
        if (existingGlobalData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country with ID " + request.getCountryId() + " not found"));
        Disease disease = diseaseRepository.findById(request.getDiseaseId())
                .orElseThrow(() -> new RuntimeException("Disease with ID " + request.getDiseaseId() + " not found"));

        GlobalData globalData = existingGlobalData.get();
        globalData.setDate(request.getDate());
        globalData.setTotalCases(request.getTotalCases());
        globalData.setNewCases(request.getNewCases());
        globalData.setTotalDeaths(request.getTotalDeaths());
        globalData.setNewDeaths(request.getNewDeaths());
        globalData.setTotalRecovered(request.getTotalRecovered());
        globalData.setNewRecovered(request.getNewRecovered());
        globalData.setActiveCases(request.getActiveCases());
        globalData.setSeriousCritical(request.getSeriousCritical());
        globalData.setTotalTests(request.getTotalTests());
        globalData.setTestsPerMillion(request.getTestsPerMillion());
        globalData.setCountry(country);
        globalData.setDisease(disease);

        GlobalData updatedGlobalData = globalDataRepository.save(globalData);
        return ResponseEntity.ok(updatedGlobalData);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une donnée globale", description = "Permet de supprimer une donnée globale en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Donnée globale supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Donnée globale non trouvée")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!globalDataRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        globalDataRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
