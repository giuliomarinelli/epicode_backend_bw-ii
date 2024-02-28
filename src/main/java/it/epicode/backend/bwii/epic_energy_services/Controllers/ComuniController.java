package it.epicode.backend.bwii.epic_energy_services.controllers;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ComuniController {
    @Autowired
private ComuneRepository comuneRepository;

    @GetMapping("/comuni/{nome}")
    public Page<Comune> getComune(@PathVariable String nome, Pageable pageable) {
return         comuneRepository.findByNomeContainingIgnoreCase(nome, pageable);

    }


}
