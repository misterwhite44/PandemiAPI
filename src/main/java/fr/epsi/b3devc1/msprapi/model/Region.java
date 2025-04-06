package fr.epsi.b3devc1.msprapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double lat;

    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
