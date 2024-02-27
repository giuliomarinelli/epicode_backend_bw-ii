package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Provincia;
import it.epicode.backend.bwii.epic_energy_services.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class Provincecontroller {
    @Autowired
   private ProvinciaRepository provinciaRepository;
    @GetMapping("/province")
    public List<Provincia> getAllProvincia(@PathVariable String sigla) {
      return    provinciaRepository.findAll();
    }

}
