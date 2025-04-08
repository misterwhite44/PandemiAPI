package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // Ignore l'ID pour le POST dans Swagger
    private Long id;  // Identifiant de type Long

    private String name;

    private Double lat;

    private Double longitude;

    @ManyToOne(cascade = CascadeType.REMOVE) // Cascade delete pour supprimer Region lorsque le Country est supprimé
    @JoinColumn(name = "country_id")
    private Country country;  // Référence à l'entité Country
}
