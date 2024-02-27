package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ComuniController {
    @Autowired
private ComuneRepository comuneRepository;

    @GetMapping("/comuni/{nome}")
    public List<Comune> getComune(@PathVariable String nome) {
return         comuneRepository.findByNomeContainingIgnoreCase(nome);

    }


}
