package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représente une région géographique associée à un pays.")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(description = "Identifiant unique de la région (généré automatiquement).", example = "1")
    private Long id;

    @Schema(description = "Nom de la région.", example = "Île-de-France")
    private String name;

    @Schema(description = "Population totale de la region", example = "67000")
    private Long population;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "country_id")
    @Schema(description = "Pays auquel appartient la région.")
    private Country country;
    
}
