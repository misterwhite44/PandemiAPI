package fr.epsi.b3devc1.msprapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(hidden = true)
public class RegionRequest {
    private String name;
    private Long population;
    private Long countryId;
}
