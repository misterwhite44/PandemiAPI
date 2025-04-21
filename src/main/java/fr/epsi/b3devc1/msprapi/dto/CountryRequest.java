package fr.epsi.b3devc1.msprapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Requête pour créer ou mettre à jour un pays.")
public class CountryRequest {

    @Schema(description = "Nom du pays.", example = "France", required = true)
    private String name;

    @Schema(description = "Code à 3 lettres du pays.", example = "FRA")
    private Integer code3;

    @Schema(description = "Population totale du pays.", example = "67000000")
    private Long population;

    @Schema(description = "ID du continent auquel appartient le pays.", example = "1", required = true)
    private Integer continentId;
}