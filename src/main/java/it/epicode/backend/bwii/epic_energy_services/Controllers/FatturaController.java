package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.FatturaDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.StatoFattura;
import it.epicode.backend.bwii.epic_energy_services.Services.FatturaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/stato")
    public Page<Fattura> getFattureByStato(@RequestParam StatoFattura stato, Pageable pageable) {
        return fatturaSv.findByStato(stato, pageable);
    }

    @GetMapping("/data")
    public Page<Fattura> getFattureByData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Pageable pageable) {
        return fatturaSv.findByData(data, pageable);
    }

    @GetMapping("/anno")
    public Page<Fattura> getFattureByAnno(@RequestParam int anno, Pageable pageable) {
        return fatturaSv.findByAnno(anno, pageable);
    }

    @GetMapping("/importo-between")
    public Page<Fattura> getFattureByImportoBetween(@RequestParam double minImporto, @RequestParam double maxImporto, Pageable pageable) {
        return fatturaSv.findByImportoBetween(minImporto, maxImporto, pageable);
    }

}
