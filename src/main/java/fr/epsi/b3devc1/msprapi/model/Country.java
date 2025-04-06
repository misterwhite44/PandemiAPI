package fr.epsi.b3devc1.msprapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String iso2;

    private String iso3;

    private Integer code3;

    private Long population;

    private String whoRegion;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;
}
