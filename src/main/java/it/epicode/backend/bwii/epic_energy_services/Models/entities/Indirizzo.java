package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoSede;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "indirizzi")
@AllArgsConstructor
@NoArgsConstructor
public class Indirizzo {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String via;

    private String civico;

    private String localita;

    private int cap;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;

    @Transient
    private String nomeComune;

    @Transient
    private String siglaProvincia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoSede tipoSede;


    public Indirizzo(String via, String civico, String localita, int cap, Comune comune, Cliente cliente, TipoSede tipoSede) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
        this.cliente = cliente;
        this.tipoSede = tipoSede;
    }

    public String toString() {
        String loc = localita;
        if (localita == null) loc = "";
        return via + " " + civico + " ( " + loc + "), " + cap + " " + comune.getNome() +
                " (" + comune.getProvincia().getSigla() + "), " + comune.getProvincia().getRegione();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indirizzo indirizzo = (Indirizzo) o;
        return tipoSede == indirizzo.tipoSede;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoSede);
    }
}
