package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FatturaController {

    @Autowired
    private FatturaService fatturaSv;

}
