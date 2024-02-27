package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UtenteRepository   extends JpaRepository<Utente, UUID>, PagingAndSortingRepository<Utente, UUID> {
}
