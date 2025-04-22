package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représente un pays avec ses informations géographiques et démographiques.")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(description = "Identifiant unique du pays (généré automatiquement).", example = "1")
    private Long id;

    @Schema(description = "Nom du pays.", example = "France", required = true)
    private String name;

    @Schema(description = "Code à 3 lettres du pays.", example = "FRA")
    private String code3;

    @Schema(description = "Population totale du pays.", example = "67000000")
    private Long population;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @Schema(description = "Continent auquel appartient le pays.")
    private Continent continent;
}
