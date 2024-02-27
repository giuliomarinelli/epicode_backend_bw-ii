package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FatturaRepository  extends JpaRepository<Fattura, Integer>, PagingAndSortingRepository<Fattura, Integer> {
}
