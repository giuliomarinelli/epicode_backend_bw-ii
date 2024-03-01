package it.epicode.backend.bwii.epic_energy_services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.epicode.backend.bwii.epic_energy_services.Controllers.FatturaController;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.FatturaDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.StatoFattura;
import it.epicode.backend.bwii.epic_energy_services.Services.ClienteService;
import it.epicode.backend.bwii.epic_energy_services.Services.FatturaService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FatturaController.class)
public class FatturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FatturaService fatturaService;

    @InjectMocks
    private FatturaController fatturaController;
    @Mock
    private ClienteService clienteService;


    @Test
     void testGetAllFacture(){
        Pageable pageable= PageRequest.of(0,15);
        Page<Fattura>page=new PageImpl<>(Arrays.asList(new Fattura(),new Fattura()));
        Mockito.when(fatturaService.getAllFacture(pageable)).thenReturn(page);
        Page<Fattura> result = fatturaController.getAllFatture(pageable);

        assertEquals(page, result);
    }
    @Test
     void testGetFatturaById() throws NotFoundException {
        UUID id= UUID.randomUUID();
        Fattura fattura= new Fattura();
        Mockito.when(fatturaService.getFactureForId(fattura.getNumero())).thenReturn(fattura);
        Fattura result=fatturaController.getFatturaById(fattura.getNumero());
        assertEquals(fattura,result);
    }

            @Test
            void   testSaveFattura() throws NotFoundException {
        UUID id=UUID.randomUUID();
        Cliente cliente= clienteService.getById(id);
        Fattura fattura=new Fattura();
        FatturaDTO fatturaDTO=new FatturaDTO(LocalDate.of(2024,12,8),1200.89,StatoFattura.CONTABILIZZATA,cliente.getId());
        Mockito.when(fatturaService.saveFacture(fatturaDTO)).thenReturn(fattura);
        Fattura result=fatturaController.saveFattura(fatturaDTO,null);
        assertEquals(fattura,result);
            }

    @Test
     void testUpdateFattura() throws NotFoundException, BadRequestException {
        UUID id= UUID.randomUUID();
        Fattura fattura= new Fattura();
        FatturaDTO fatturaDTO=new FatturaDTO(LocalDate.of(2024,10,8),1200.99,StatoFattura.PAGATA,UUID.randomUUID());
        Cliente cliente=new Cliente();

        Mockito.when(clienteService.getById(id)).thenReturn(cliente);
         Mockito.when(fatturaService.updateFacture(1,fatturaDTO)).thenReturn(fattura);
        Fattura result=fatturaController.updateFattura(1,fatturaDTO,null);
        assertEquals(fattura,result);

    }

    @Test
    public void testDeleteFattura() throws Exception {
        // Performing the request and validating the response
        mockMvc.perform(delete("/api/fatture/delete/1"))
                .andExpect(status().isOk());
    }
}

