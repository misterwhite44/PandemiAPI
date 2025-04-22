package fr.epsi.b3devc1.msprapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Requête pour créer ou mettre à jour une donnée globale.")
public class GlobalDataRequest {

    @Schema(description = "Date des données.", example = "2023-01-01", required = true)
    private Date date;

    @Schema(description = "Nombre total de cas.", example = "1000000")
    private Integer totalCases;

    @Schema(description = "Nombre de nouveaux cas.", example = "5000")
    private Integer newCases;

    @Schema(description = "Nombre total de décès.", example = "50000")
    private Integer totalDeaths;

    @Schema(description = "Nombre de nouveaux décès.", example = "200")
    private Integer newDeaths;

    @Schema(description = "Nombre total de personnes guéries.", example = "950000")
    private Integer totalRecovered;

    @Schema(description = "Nombre de nouvelles guérisons.", example = "3000")
    private Integer newRecovered;

    @Schema(description = "Nombre de cas actifs.", example = "50000")
    private Integer activeCases;

    @Schema(description = "Nombre de cas graves ou critiques.", example = "1000")
    private Integer seriousCritical;

    @Schema(description = "Nombre total de tests effectués.", example = "2000000")
    private Long totalTests;

    @Schema(description = "Nombre de tests par million d'habitants.", example = "30000")
    private Integer testsPerMillion;

    @Schema(description = "ID du pays associé.", example = "1", required = true)
    private Long countryId;

    @Schema(description = "ID de la region associée.", example = "1", required = true)
    private Long regionId;

    @Schema(description = "ID de la maladie associée.", example = "1", required = true)
    private Integer diseaseId;
}