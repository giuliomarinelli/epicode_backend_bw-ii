package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "fatture")
@AllArgsConstructor
@NoArgsConstructor
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "numero_gen")
    @SequenceGenerator(name = "numero_gen", allocationSize = 1, initialValue = 1)
    @Setter(AccessLevel.NONE)
    private int numero;

    private LocalDate data;

    private double importo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}