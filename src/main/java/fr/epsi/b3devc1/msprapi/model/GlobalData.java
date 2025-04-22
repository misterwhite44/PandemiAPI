package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représente les données globales liées à une maladie dans un pays.")
public class GlobalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(description = "Identifiant unique des données globales (généré automatiquement).", example = "1")
    private Integer id;

    @Schema(description = "Date des données.", example = "2023-01-01")
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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "country_id")
    @Schema(description = "Pays auquel les données globales sont associées.")
    private Country country;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "region_id")
    @Schema(description = "Région à laquelle les données globales sont associées.")
    private Region region;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "disease_id")
    @Schema(description = "Maladie à laquelle les données globales sont associées.")
    private Disease disease;
}
