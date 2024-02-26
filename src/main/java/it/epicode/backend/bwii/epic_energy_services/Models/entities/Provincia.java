package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "province")
public class Provincia {
    @Id
    @Setter(AccessLevel.NONE)
    private String sigla;

    private String nome;

    private String regione;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "provincia")
    private List<Comune> comuni = new ArrayList<>();



}
