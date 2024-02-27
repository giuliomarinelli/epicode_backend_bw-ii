package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>, PagingAndSortingRepository<Cliente, UUID> {
    Page<Cliente> findAllByNomeContatto                 (Pageable pageable);

    Page<Cliente> findAllByFatturatoAnnuale             (Pageable pageable);

    Page<Cliente> findAllByDataInserimento              (Pageable pageable);

    Page<Cliente> findAllByDataUltimoContatto           (Pageable pageable);

    Page<Cliente> findAllByIndirizzoSedeLegaleProvincia (Pageable pageable);

    Page<Cliente> findByFatturatoAnnualeBetween(
            double minFatturato, double maxFatturato, Pageable pageable
    );

    Page<Cliente> findByDataInserimentoBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable
    );

    Page<Cliente> findByDataUltimoContattoBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable
    );

    Page<Cliente> findByNomeContattoContainingIgnoreCase(
            String nomeParziale, Pageable pageable
    );

}
