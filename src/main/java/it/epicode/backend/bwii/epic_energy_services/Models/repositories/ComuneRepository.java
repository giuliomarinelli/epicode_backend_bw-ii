package it.epicode.backend.bwii.epic_energy_services.Models.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, UUID> {
}
