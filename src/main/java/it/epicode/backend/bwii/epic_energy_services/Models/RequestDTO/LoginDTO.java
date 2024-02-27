package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;


import jakarta.validation.constraints.NotBlank;


public record LoginDTO(
        @NotBlank(message = "Lo username è obbligatorio e non può essere un campo vuoto")
        String username,
        @NotBlank(message = "La password è obbligatoria e non puà essere un campo vuoto")
        String password
        
) {}
