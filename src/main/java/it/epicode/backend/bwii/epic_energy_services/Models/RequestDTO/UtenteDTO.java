package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import it.epicode.backend.bwii.epic_energy_services.Models.enums.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

public record UtenteDTO(
        @NotBlank(message = "Il campo username non può essere vuoto/null")
        String username,
        @Email(regexp = "^[w-.]+@([w-]+.)+[w-]{2,4}$", message = "email non valida")
        String email,
        @NotBlank(message = "Il campo password non può essere vuoto/null")
        String password,
        @NotBlank(message = "Il campo nome non può essere vuoto/null")
        String nome,
        @NotBlank(message = "Il campo cognome non può essere vuoto/null")
        String cognome,
        @NotBlank(message = "Il campo avatar non può essere vuoto/null")
        String avatar

) {
}
