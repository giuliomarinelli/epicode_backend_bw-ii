package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.StatoFattura;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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

    private StatoFattura stato;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Transient
    private UUID clienteId;



    public Fattura(LocalDate data, double importo, Cliente cliente, StatoFattura stato) {
        this.data = data;
        this.importo = importo;
        this.cliente = cliente;
        this.stato = stato;

    }

    @Override
    public String toString() {
        return "";
    }
}
