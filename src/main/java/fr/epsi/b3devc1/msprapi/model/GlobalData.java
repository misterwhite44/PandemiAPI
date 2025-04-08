package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // Ignore l'ID pour le POST dans Swagger
    private Integer id;

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

    @ManyToOne(cascade = CascadeType.REMOVE) // Cascade delete pour supprimer GlobalData lorsque le Country est supprimé
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(cascade = CascadeType.REMOVE) // Cascade delete pour supprimer GlobalData lorsque la Disease est supprimée
    @JoinColumn(name = "disease_id")
    private Disease disease;
}
