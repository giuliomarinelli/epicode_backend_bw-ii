package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IndirizzoRepository  extends JpaRepository<Indirizzo, UUID>, PagingAndSortingRepository<Indirizzo, UUID> {
}
