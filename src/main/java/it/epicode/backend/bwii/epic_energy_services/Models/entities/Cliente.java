package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String ragioneSociale;

    @Column(unique = true)
    private long partitaIva;

    @Column(unique = true)
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

    @JsonIgnore
    @OneToOne(mappedBy = "cliente")
    private Indirizzo sedeLegale;

    @Transient
    private String indirizzoSedeLegale;

    @JsonIgnore
    @OneToOne(mappedBy = "cliente")
    private Indirizzo sedeOperativa;

    @Transient
<<<<<<< HEAD
    private String indirizzoSedeOperativa = sedeOperativa.toString();

=======
    private String indirizzoSedeOperativa;

    public void setIndirizzoSedeLegaleAndSedeOperativa() {
        if (sedeLegale != null)
            indirizzoSedeLegale = sedeLegale.toString();
        if (indirizzoSedeOperativa != null)
            indirizzoSedeOperativa = sedeOperativa.toString();
    }
>>>>>>> 9f008a2e9e50f5ca90ba505974e9c334453a1a50
}
