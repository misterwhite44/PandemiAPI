package fr.epsi.b3devc1.msprapi.dto;

import lombok.Data;

@Data
public class RegionRequest {
    private String name;
    private Long countryId;
}
