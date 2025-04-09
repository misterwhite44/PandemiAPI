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
    @JsonIgnore
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "country_id")
    private Country country;
}
