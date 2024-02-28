package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.StatoFattura;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(
        @NotBlank(message = "Data fattura obbligatorio")
        LocalDate data,
        @NotBlank(message = "Importo fattura obbligatorio")
        double importo,
        @NotNull(message = "Stato fattura obbligatorio")
        StatoFattura stato,

        @NotBlank(message = "clienteId obbligatorio")
        UUID clienteId
) {}
