package it.epicode.backend.bwii.epic_energy_services.Services;



import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import it.epicode.backend.bwii.epic_energy_services.repositories.ClienteRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRp;

    @Autowired
    private MailService mailService;

    public Page<Utente> getAll(Pageable pageable){
        return utenteRp.findAll(pageable);
    }

    public Utente getById(UUID id)throws BadRequestException {
        return utenteRp.findById(id)
                .orElseThrow(()-> new BadRequestException("L'utente con id"+id+"è inesistente"));
    }

    public Utente save(UtenteDTO utente)throws BadRequestException{
        Utente u = new Utente();
        u.setNome(utente.nome());
        u.setCognome(utente.cognome());
        u.setEmail(utente.email());
        u.setPassword(utente.password());
        u.setUsername(utente.username());
        u.setAvatar(utente.avatar());
        mailService.sendEmail(
                u.getEmail(),
                "ISCRIZIONE EFFETTUATA CON SUCCESSO",
                "Benvenuto"+u.getNome()+", ti diamo il benvenuto in Epic Energy Services"
        );

        return utenteRp.save(u);
    }
    public Utente update(UUID id, UtenteDTO utente)throws BadRequestException{
        Utente u = getById(id);

        u.setNome(utente.nome());
        u.setCognome(utente.cognome());
        u.setEmail(utente.email());
        u.setPassword(utente.password());
        u.setUsername(utente.username());
        u.setAvatar(utente.avatar());
        mailService.sendEmail(
                u.getEmail(),
                "MODIFICA EFFETTUATA CON SUCCESSO",
                "Ciao"+u.getNome()+", hai modificato il tuo account con successo"
        );

        return utenteRp.save(u);
    }
    public void delete(UUID id)throws BadRequestException{
        Utente u = getById(id);
        utenteRp.delete(u);
    }


}
