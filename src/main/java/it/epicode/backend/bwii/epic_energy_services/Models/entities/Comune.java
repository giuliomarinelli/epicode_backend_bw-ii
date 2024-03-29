package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "comune", fetch = FetchType.EAGER)
    private List<Indirizzo> indirizzi;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }


}
