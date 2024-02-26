package it.epicode.backend.bwii.epic_energy_services.Models.entities;

import it.epicode.backend.bwii.epic_energy_services.Models.enums.Ruolo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;

    private List<Ruolo> ruolo;
}
