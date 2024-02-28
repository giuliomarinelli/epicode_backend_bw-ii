package it.epicode.backend.bwii.epic_energy_services.Services;


import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteUpdateDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import it.epicode.backend.bwii.epic_energy_services.repositories.ClienteRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.time.LocalDate;
import java.util.UUID;


@Service
public class UtenteService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UtenteRepository utenteRp;

    @Autowired
    private MailService mailService;

    public Page<Utente> getAll(Pageable pageable) {
        return utenteRp.findAll(pageable);
    }

    public Utente getById(UUID id) throws BadRequestException {
        return utenteRp.findById(id)
                .orElseThrow(() -> new BadRequestException("L'utente con id" + id + "è inesistente"));
    }

    public Utente save(UtenteDTO utente) throws BadRequestException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, InternalServerErrorException {
        Utente u = new Utente();
        u.setNome(utente.nome());
        u.setCognome(utente.cognome());
        u.setEmail(utente.email());
        u.setUsername(utente.username());
        u.setPassword(encoder.encode(utente.password()));
        try {
            utenteRp.save(u);
            mailService.sendEmail(
                    u.getEmail(),
                    "ISCRIZIONE EFFETTUATA CON SUCCESSO",
                    "Benvenuto " + u.getNome() + ", ti diamo il benvenuto in Epic Energy Services"
            );
        } catch (DataIntegrityViolationException e) {
            if (utenteRp.getAllEmails().contains(u.getEmail()))
                throw new it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException("email già esistente, impossibile creare");
            if (utenteRp.getAllUsernames().contains(u.getUsername()))
                throw new it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException("username già esistente, impossibile creare");
            throw new InternalServerErrorException("Errore di violazione dell'* integrità dei dati: " + e.getMessage());
        }
        return u;
    }

    public Utente update(UUID id, UtenteUpdateDTO utente) throws BadRequestException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, InternalServerErrorException {
        Utente u = getById(id);

        u.setNome(utente.nome());
        u.setCognome(utente.cognome());
        u.setEmail(utente.email());
        u.setUsername(utente.username());
        try {

            utenteRp.save(u);
            mailService.sendEmail(
                    u.getEmail(),
                    "MODIFICA EFFETTUATA CON SUCCESSO",
                    "Ciao" + u.getNome() + ", hai modificato il tuo account con successo"
            );
            return(u);
        } catch (DataIntegrityViolationException e) {
            if (utenteRp.getAllUsernames().contains(u.getUsername()) || utenteRp.getAllEmails().contains(u.getEmail()))
                throw new it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException("Lo username e/o la password impostati esistono già. Impossibile aggiornare");
            throw new InternalServerErrorException("Errore di violazione dell' integrità dei dati: " + e.getMessage());
        }
    }

    public void updatePassword(String oldPassword, String newPassword, Utente utente) throws UnauthorizedException {
        if (!encoder.matches(oldPassword, utente.getPassword()))
            throw new UnauthorizedException("La vecchia password non è corretta. Impossibile cambiare password");
        utente.setPassword(encoder.encode(newPassword));
        utenteRp.save(utente);
    }

    public Utente updateAfterUpload(Utente utente, String url) {
        utente.setAvatar(url);
        return utenteRp.save(utente);
    }

    public void delete(UUID id) throws BadRequestException {
        Utente u = getById(id);
        utenteRp.delete(u);
    }


}
