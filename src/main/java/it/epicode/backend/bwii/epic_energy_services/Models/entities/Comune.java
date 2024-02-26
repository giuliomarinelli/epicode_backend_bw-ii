package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comuni")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sigla_provincia", nullable = false)
    private Provincia provincia;

    @OneToMany(mappedBy = "comune", fetch = FetchType.EAGER)
    private List<Indirizzo> indirizzi;




}
