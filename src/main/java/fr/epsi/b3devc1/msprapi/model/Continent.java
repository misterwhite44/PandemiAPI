package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représente un continent.")
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(description = "Identifiant unique du continent (généré automatiquement).", example = "1")
    private Integer id;

    @Schema(description = "Nom du continent.", example = "Europe", required = true)
    private String name;
}
