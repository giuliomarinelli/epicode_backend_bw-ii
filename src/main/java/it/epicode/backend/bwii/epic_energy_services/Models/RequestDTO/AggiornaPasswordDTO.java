package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import jakarta.validation.constraints.NotBlank;

public record AggiornaPasswordDTO(
        @NotBlank(message = "Il campo vecchiaPassword non può essere vuoto/null")
        String vecchiaPassword,
        @NotBlank(message = "Il campo vecchiaPassword non può essere vuoto/null")
        String nuovaPassword
) {}
