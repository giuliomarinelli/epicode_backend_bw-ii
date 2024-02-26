package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name = "indirizzi")
@AllArgsConstructor
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String via;

    private String civico;

    private String localita;

    private int cap;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
