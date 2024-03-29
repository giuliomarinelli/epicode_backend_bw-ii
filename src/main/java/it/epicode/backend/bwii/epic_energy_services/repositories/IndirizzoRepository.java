package it.epicode.backend.bwii.epic_energy_services.repositories;


import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IndirizzoRepository  extends JpaRepository<Indirizzo, UUID>, PagingAndSortingRepository<Indirizzo, UUID> {
    public List<Indirizzo> findByCliente(Cliente cliente);
}
