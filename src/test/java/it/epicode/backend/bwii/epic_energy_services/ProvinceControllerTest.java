package it.epicode.backend.bwii.epic_energy_services;

import it.epicode.backend.bwii.epic_energy_services.Controllers.Provincecontroller;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Provincia;
import it.epicode.backend.bwii.epic_energy_services.repositories.ProvinciaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProvinceControllerTest {
    @Mock
    private ProvinciaRepository provinciaRepository;
    @InjectMocks
    private Provincecontroller provincecontroller;

    @Test
    void TestProvinceGetAll() throws NotFoundException {
        List<Provincia> provincias=new ArrayList<>();
        Mockito.when(provinciaRepository.findAll()).thenReturn(provincias);    }
}
