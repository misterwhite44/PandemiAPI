package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représente une maladie.")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Schema(description = "Identifiant unique de la maladie (généré automatiquement).", example = "1")
    private Integer id;

    @Schema(description = "Nom de la maladie.", example = "COVID-19", required = true)
    private String name;
}
