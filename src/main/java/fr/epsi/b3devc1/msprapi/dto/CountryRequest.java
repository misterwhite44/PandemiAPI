package fr.epsi.b3devc1.msprapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(hidden = true)
public class CountryRequest {

    private String name;

    private String code3;

    private Long population;

    private Integer continentId;
}