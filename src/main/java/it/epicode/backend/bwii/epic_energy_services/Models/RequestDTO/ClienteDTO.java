package it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO;

import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;


public record ClienteDTO (
        @NotBlank(message = "Il campo ragione sociale non può essere vuoto/null")
        String ragioneSociale,
        @NotBlank(message = "Il campo partita Iva non può essere vuoto/null")
        Long partitaIva,
        @NotBlank(message = "Il campo email non può essere vuoto/null")
        @Email(regexp = "^[w-.]+@([w-]+.)+[w-]{2,4}$", message = "email non valida")
        String email,
        @NotBlank(message = "Il campo data Inserimento non può essere vuoto/null")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "formato data non valido")
        String dataInserimento,
        @NotBlank(message = "Il campo data Ultimo Contatto non può essere vuoto/null")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$" , message = "formato data non valido")
        String dataUltimoContatto,

        @NotNull(message = "Il campo fatturato annuale non può essere null")
        @Size(min = 0, message = "Il valore deve essere maggiore di 0")
        Double fatturatoAnnuale,

        @NotBlank(message = "Il campo pec non può essere vuoto/null")
        String pec,
        @NotNull(message = "Il campo telefono non può essere null")
        @Pattern(regexp = "^((00|\\+)39[\\. ]??)??3\\d{2}[\\. ]??\\d{6,7}$", message = "formato non valido")
        Long telefono,

        @NotBlank(message = "Il campo email contatto non può essere vuoto/null")
        @Email(regexp = "^[w-.]+@([w-]+.)+[w-]{2,4}$", message = "email non valida")
        String emailContatto,
        @NotBlank(message = "Il campo nome contatto non può essere vuoto/null")
        String nomeContatto,
        @NotBlank(message = "Il campo cognome contatto non può essere vuoto/null")
        String cognomeContatto,
        @NotNull(message = "Il campo telefono contatto non può essere null")
        @Pattern(regexp = "^((00|\\+)39[\\. ]??)??3\\d{2}[\\. ]??\\d{6,7}$", message = "formato non valido")
        Long telefonoContatto,
        @NotBlank(message = "Il campo logo aziendale non può essere vuoto/null")
        String logoAziendale,
        @NotBlank(message = "Il campo tipo cliente non può essere vuoto/null")
        String tipoCliente,
        @NotBlank(message = "Il campo sede legale non può essere vuoto/null")
        String sedeLegale,
        @NotBlank(message = "Il campo sede operativa non può essere vuoto/null")
        String sedeOperativa
){

}
