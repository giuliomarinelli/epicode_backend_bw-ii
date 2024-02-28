package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


public record IndirizzoDTO(

        @NotBlank(message = "Via obbligatoria")
        String via,
        @NotBlank(message = "Numero civico obbligatorio")
        String civico,

        String localita,
        @NotBlank(message = "Il codice di avviamento postale è obbligatorio")
        int cap,
        @NotBlank(message = "Il comune è obbligatorio")
        String nomeComune,
        @NotBlank(message = "La sigla della provincia è obbligatoria")
        @Pattern(regexp = "[A-Z]{2}", message = "La sigla della provincia deve essere costituita da due lettere maiuscole")
        String siglaProvincia
) {
}
