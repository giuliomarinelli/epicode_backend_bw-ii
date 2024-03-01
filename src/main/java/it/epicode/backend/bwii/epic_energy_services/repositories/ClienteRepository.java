package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>, PagingAndSortingRepository<Cliente, UUID> {
    Page<Cliente> findByNomeContatto(String nomeContatto, Pageable pageable);

    Page<Cliente> findByFatturatoAnnuale(double fatturatoAnnuale, Pageable pageable);

    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);

    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);

    Page<Cliente> findAllByOrderByNomeContattoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByFatturatoAnnualeAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataInserimentoAsc(Pageable pageable);

    Page<Cliente> findAllByOrderByDataUltimoContattoAsc(Pageable pageable);

//    @Query("SELECT c FROM Cliente c WHERE c.sedeLegale.comune.provincia.sigla = :siglaProvincia")
//    Page<Cliente> findByIndirizzoSedeLegaleProvincia(String siglaProvincia, Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE c.fatturatoAnnuale BETWEEN :minFatturato AND :maxFatturato")
    Page<Cliente> findByFatturatoAnnualeBetween(
            double minFatturato, double maxFatturato, Pageable pageable
    );
    @Query("SELECT c FROM Cliente c WHERE c.dataInserimento BETWEEN :startDate AND :endDate")
    Page<Cliente> findByDataInserimentoBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable
    );
    @Query("SELECT c FROM Cliente c WHERE c.dataUltimoContatto BETWEEN :startDate AND :endDate")
    Page<Cliente> findByDataUltimoContattoBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable
    );
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nomeContatto) LIKE LOWER(CONCAT('%', :nomeContatto, '%'))")
    Page<Cliente> findByNomeContattoContainingIgnoreCase(
            String nomeContatto, Pageable pageable
    );

    @Query("SELECT c.email FROM Cliente c")
    public List<String> getAllEmails();

    @Query("SELECT c.pec FROM Cliente c")
    public List<String> getAllPecs();

}
