package it.epicode.backend.bwii.epic_energy_services.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.AggiornaPasswordDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteUpdateDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.ResponseDTO.ConfirmRes;
import it.epicode.backend.bwii.epic_energy_services.Models.ResponseDTO.DeleteRes;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Services.UtenteService;
import it.epicode.backend.bwii.epic_energy_services.security.JwtTools;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


@RestController
public class ProfiloController {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    JwtTools jwtTools;

    @Autowired
    UtenteService utenteSvc;

    @PatchMapping("/profile/upload-immagine-profilo")
    public Utente upload(@RequestParam("file") MultipartFile file) throws IOException, UnauthorizedException {
        UUID userId = jwtTools.extractUserIdFromReq();
        Utente u = utenteSvc.getById(userId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url");
        return utenteSvc.updateAfterUpload(u, url);
    }

    @GetMapping("/profile")
    public Utente getProfile() throws UnauthorizedException, BadRequestException {
        UUID userId = jwtTools.extractUserIdFromReq();
        return utenteSvc.getById(userId);
    }

    @PatchMapping("/profile/aggiorna-password")
    public ConfirmRes aggiornaPassword(@Validated @RequestBody AggiornaPasswordDTO aggiornaPasswordDTO, BindingResult validation) throws it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, UnauthorizedException, BadRequestException {
        HandlerException.exception(validation);
        UUID userId = jwtTools.extractUserIdFromReq();
        Utente u = utenteSvc.getById(userId);
        utenteSvc.updatePassword(aggiornaPasswordDTO.vecchiaPassword(), aggiornaPasswordDTO.nuovaPassword(), u);
        return new ConfirmRes("Password aggiornata con successo", HttpStatus.OK);
    }

    @PutMapping("/profile")
    public Utente update(@Validated @RequestBody UtenteUpdateDTO utenteUpdateDTO, BindingResult validation) throws it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, UnauthorizedException, BadRequestException, InternalServerErrorException {
        HandlerException.exception(validation);
        UUID userId = jwtTools.extractUserIdFromReq();
        return utenteSvc.update(userId, utenteUpdateDTO);
    }

    @DeleteMapping("/profile")
    public DeleteRes delete() throws UnauthorizedException, BadRequestException {
        UUID userId = jwtTools.extractUserIdFromReq();
        Utente u = utenteSvc.getById(userId);
        String username = u.getUsername();
        utenteSvc.delete(userId);
        return new DeleteRes("Utente (username='" + username + "', id='" + userId + "') cancellato con successo");
    }
}
