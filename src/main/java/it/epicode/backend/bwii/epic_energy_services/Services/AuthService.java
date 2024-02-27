package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.ResponseDTO.AccessTokenRes;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import it.epicode.backend.bwii.epic_energy_services.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UtenteRepository utenteRp;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtTools jwtTools;

    public Utente register(UtenteDTO utenteDTO) throws BadRequestException, InternalServerErrorException {
        Utente u = new Utente(utenteDTO.username(), utenteDTO.email(), encoder.encode(utenteDTO.password()),
                utenteDTO.nome(), utenteDTO.cognome());
        try {
            return utenteRp.save(u);
        } catch (DataIntegrityViolationException e) {
            if (utenteRp.getAllEmails().contains(u.getEmail()))
                    throw new BadRequestException("email già esistente, impossibile creare");
            if (utenteRp.getAllUsernames().contains(u.getUsername()))
                    throw new BadRequestException("username già esistente, impossibile creare");
            throw new InternalServerErrorException("Errore di violazione dell'* integrità dei dati: " + e.getMessage());

        }
    }

    public Optional<Utente> findByUserId(UUID userId) {
        return utenteRp.findById(userId);
    }

    public AccessTokenRes login(String username, String password) throws UnauthorizedException {
        Utente u = utenteRp.findByUsername(username).orElseThrow(
                () -> new UnauthorizedException("Email/password errati")
        );
        if (!encoder.matches(password, u.getPassword()))
            throw new UnauthorizedException("Email/password errati");
        return new AccessTokenRes(jwtTools.createToken(u));
    }


}
