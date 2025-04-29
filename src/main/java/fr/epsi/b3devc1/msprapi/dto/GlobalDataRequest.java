package fr.epsi.b3devc1.msprapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(hidden = true)
public class GlobalDataRequest {

    private Date date;

    private Integer totalCases;

    private Integer newCases;

    private Integer totalDeaths;

    private Integer newDeaths;

    private Integer totalRecovered;

    private Integer newRecovered;

    private Integer activeCases;

    private Integer seriousCritical;

    private Long totalTests;

    private Integer testsPerMillion;

    private Long countryId;

    private Integer diseaseId;
}