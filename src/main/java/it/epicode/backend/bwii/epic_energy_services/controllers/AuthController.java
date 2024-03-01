package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.LoginDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.ResponseDTO.AccessTokenRes;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authSvc;


    @PostMapping("/auth/register")
    public Utente register(@Validated @RequestBody UtenteDTO utenteDTO, BindingResult validation) throws BadRequestException, InternalServerErrorException {
        System.out.println(utenteDTO);
        HandlerException.exception(validation);
        return authSvc.register(utenteDTO);
    }

    @PostMapping("/auth/login")
    public AccessTokenRes login(@Validated @RequestBody LoginDTO loginDTO, BindingResult validation) throws BadRequestException, UnauthorizedException {
        HandlerException.exception(validation);
        return authSvc.login(loginDTO.username(), loginDTO.password());
    }

}
