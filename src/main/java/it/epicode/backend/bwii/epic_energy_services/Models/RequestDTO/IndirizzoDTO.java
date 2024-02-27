package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public record IndirizzoDTO (

    @NotBlank(message = "Via obbligatoria")
     String via,
    @NotBlank(message = "Numero civico obbligatorio")
     String civico,
    @NotBlank(message = "Localita obbligatorio")
     String localita,
    @NotBlank(message = "Il codice di avviamento postale è obbligatorio")
     int cap,
    @NotBlank(message = "Il comune è obbligatorio")
     Comune comune,
    @NotBlank(message = "Il cliente  è obbligatorio")
     Cliente cliente
){}
