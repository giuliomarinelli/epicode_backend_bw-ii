package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    public Utente getUtenteById(UUID id)throws NotFoundException {
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente non trovato"));
    }
}
