package it.epicode.backend.bwii.epic_energy_services;

import it.epicode.backend.bwii.epic_energy_services.Controllers.AuthController;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.LoginDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.UtenteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;

import static org.junit.Assert.assertEquals;


public class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController controller;
     @Test
    void registerControllerTest() throws BadRequestException, InternalServerErrorException {
        Utente utente=new Utente();
        UtenteDTO utenteDTO=new UtenteDTO("test","test.1@gmail.com","123","testn","test");
        Mockito.when(authService.register(utenteDTO)).thenReturn(utente);
        Utente utente1=controller.register(utenteDTO,null);
        assertEquals(utente,utente1);

    }
  //  @Test
    //void LoginControllerTest() throws BadRequestException, InternalServerErrorException, UnauthorizedException {

      //  User user=new User("dd","123");
        //UtenteDTO utenteDTO=new UtenteDTO("test","test.1@gmail.com","123","testn","test");

        //LoginDTO loginDTO=new LoginDTO("test","test.1@gmail.");
        //Mockito.when(authService.findByUserId()
                //login("test","test")).thenReturn(loginDTO);
    }




