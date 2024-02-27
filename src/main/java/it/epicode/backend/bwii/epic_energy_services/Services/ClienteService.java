package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import it.epicode.backend.bwii.epic_energy_services.repositories.ClienteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRp;

    @Autowired
    private MailService mailService;

    public Page<Cliente> getAll(Pageable pageable){
        return clienteRp.findAll(pageable);
    }

    public Cliente getById(UUID id)throws BadRequestException{
        return clienteRp.findById(id)
                .orElseThrow(()-> new BadRequestException("Il cliente con id"+id+"è inesistente"));
    }

    public Cliente save(ClienteDTO cliente)throws BadRequestException{
        Cliente c = new Cliente();
        c.setRagioneSociale(cliente.ragioneSociale());
        c.setPartitaIva(cliente.partitaIva());
        c.setDataInserimento(LocalDate.parse(cliente.dataInserimento()));
        c.setDataUltimoContatto(LocalDate.parse(cliente.dataUltimoContatto()));
        c.setFatturatoAnnuale(cliente.fatturatoAnnuale());
        c.setPec(cliente.pec());
        c.setTelefono(cliente.telefono());
        c.setEmailContatto(cliente.emailContatto());
        c.setNomeContatto(cliente.nomeContatto());
        c.setCognomeContatto(cliente.cognomeContatto());
        c.setTelefonoContatto(cliente.telefonoContatto());
        c.setLogoAziendale(cliente.logoAziendale());
        c.setTipoCliente(TipoCliente.valueOf(cliente.tipoCliente()));

        c.setEmail(cliente.email());
        mailService.sendEmail(
                c.getEmail(),
                "ISCRIZIONE EFFETTUATA CON SUCCESSO",
                "Benvenuto"+c.getNomeContatto()+", ti diamo il benvenuto in Epic Energy Services"
                );

        return clienteRp.save(c);
    }
    public Cliente update(UUID id, ClienteDTO cliente)throws BadRequestException{
        Cliente c = getById(id);
        c.setRagioneSociale(cliente.ragioneSociale());
        c.setPartitaIva(cliente.partitaIva());
        c.setDataInserimento(LocalDate.parse(cliente.dataInserimento()));
        c.setDataUltimoContatto(LocalDate.parse(cliente.dataUltimoContatto()));
        c.setFatturatoAnnuale(cliente.fatturatoAnnuale());
        c.setPec(cliente.pec());
        c.setTelefono(cliente.telefono());
        c.setEmailContatto(cliente.emailContatto());
        c.setNomeContatto(cliente.nomeContatto());
        c.setCognomeContatto(cliente.cognomeContatto());
        c.setTelefonoContatto(cliente.telefonoContatto());
        c.setLogoAziendale(cliente.logoAziendale());
        c.setTipoCliente(TipoCliente.valueOf(cliente.tipoCliente()));
        mailService.sendEmail(
                c.getEmail(),
                "MODIFICA EFFETTUATA CON SUCCESSO",
                "Ciao"+c.getNomeContatto()+", il tuo profilo cliente con id"+ id +"è stato modificato"
        );
        return clienteRp.save(c);
    }
    public void delete(UUID id)throws BadRequestException{
        Cliente c = getById(id);
        clienteRp.delete(c);
    }
}
