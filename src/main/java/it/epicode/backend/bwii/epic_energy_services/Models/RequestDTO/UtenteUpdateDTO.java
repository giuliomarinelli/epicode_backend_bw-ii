package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UtenteUpdateDTO(
        @NotBlank(message = "Il campo username non può essere vuoto/null")
        String username,
        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "email non valida")
        String email,

        @NotBlank(message = "Il campo nome non può essere vuoto/null")
        String nome,
        @NotBlank(message = "Il campo cognome non può essere vuoto/null")
        String cognome

) {
}
