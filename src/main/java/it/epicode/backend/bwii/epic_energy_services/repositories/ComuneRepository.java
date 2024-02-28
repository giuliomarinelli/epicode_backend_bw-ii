package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, UUID>, PagingAndSortingRepository<Comune, UUID> {

    public Page<Comune> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    @Query("SELECT c FROM Comune c WHERE c.nome = :nome AND c.provincia.sigla = :provincia")
    public Optional<Comune> findByNomeAndProvincia(String nome, String provincia);

}
