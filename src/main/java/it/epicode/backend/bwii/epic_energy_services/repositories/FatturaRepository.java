package it.epicode.backend.bwii.epic_energy_services.repositories;



import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;




import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer>, PagingAndSortingRepository<Fattura, Integer> {

    Page<Fattura> findByClienteOrderByDataAsc       (Cliente cliente, Pageable pageable);

    Page<Fattura> findByStatoOrderByDataAsc         (String stato, Pageable pageable);

    Page<Fattura> findByData                        (LocalDate data, Pageable pageable);

    Page<Fattura> findByAnnoOrderByDataAsc          (int anno, Pageable pageable);

    Page<Fattura> findByImportoBetweenOrderByDataAsc(double minImporto, double maxImporto, Pageable pageable);

}
