package it.epicode.backend.bwii.epic_energy_services.controllers;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.FatturaDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Services.FatturaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaSv;

    @GetMapping("")
    public Page<Fattura> getAllFatture(Pageable pageable){
        return fatturaSv.getAllFacture(pageable);
    }

    @GetMapping("/id")
    public Fattura getFatturaById(@RequestParam int id)throws NotFoundException {
        return fatturaSv.getFactureForId(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura saveFattura(@RequestBody FatturaDTO fattura, BindingResult bindingResult)throws NotFoundException{
        HandlerException.notFoundException(bindingResult);
        return fatturaSv.saveFacture(fattura);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable int id, @RequestBody FatturaDTO fattura, BindingResult bindingResult) throws NotFoundException, BadRequestException {
        HandlerException.notFoundException(bindingResult);
        return fatturaSv.updateFacture(id, fattura);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFattura(@PathVariable int id)throws NotFoundException{
        fatturaSv.deleteFacture(id);
    }

}
