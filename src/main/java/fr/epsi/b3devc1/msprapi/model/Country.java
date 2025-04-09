package fr.epsi.b3devc1.msprapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;

    private String iso2;

    private Integer code3;

    private Long population;

    private String whoRegion;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;
}
