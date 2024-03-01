package it.epicode.backend.bwii.epic_energy_services;

import it.epicode.backend.bwii.epic_energy_services.Controllers.ComuniController;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.repositories.ComuneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

public class ComuniControllerTest {
    @Mock
    private ComuneRepository comuneRepository;
    @InjectMocks
    private ComuniController comuniController;
        @Test
            void TestGetAllComuni(){
                Pageable pageable = PageRequest.of(0, 10);
                Page<Comune> page = new PageImpl<>(Arrays.asList(new Comune(), new Comune()));

                Mockito.when(comuneRepository.findAll(pageable)).thenReturn(page);

            }
}
