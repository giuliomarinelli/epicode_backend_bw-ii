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

    @Override
    public String toString() {
        String loc = localita;
        if (localita == null) loc = "";
        return via + " " + civico + " ( " + loc + "), " + cap + " " + comune +
                " (" + comune.getProvincia().getSigla() + "), " + comune.getProvincia().getRegione();
    }

    public Indirizzo(String via, String civico, String localita, int cap,Comune comune,Cliente cliente){
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune=comune;
        this.cliente=cliente;
}}
