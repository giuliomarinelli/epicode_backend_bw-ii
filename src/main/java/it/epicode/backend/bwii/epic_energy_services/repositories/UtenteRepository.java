package it.epicode.backend.bwii.epic_energy_services.repositories;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID>, PagingAndSortingRepository<Utente, UUID> {

    public Optional<Utente> findByEmail(String email);
    @Query("SELECT u.email FROM Utente u")
    public List<String> getAllEmails();
    @Query("SELECT u.username FROM Utente u")
    public List<String> getAllUsernames();

    public Optional<Utente> findByUsername(String username);

}
