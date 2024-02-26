package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String ragioneSociale;

    private long partitaIva;

    private String email;

    private LocalDate dataInserimento;

    private LocalDate dataUltimoContatto;

    private double fatturatoAnnuale;

    private String pec;

    private long telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private long telefonoContatto;

    private String logoAziendale;

    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fatture;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToOne(mappedBy = "cliente")
    private Indirizzo sedeLegale;

    @OneToOne(mappedBy = "cliente")
    private Indirizzo sedeOperativa;



}
