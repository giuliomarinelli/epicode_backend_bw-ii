package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.*;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoCliente;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.TipoSede;
import it.epicode.backend.bwii.epic_energy_services.repositories.ClienteRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.ComuneRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.IndirizzoRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.ProvinciaRepository;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class ClienteService {

    public static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRp;

    @Autowired
    private MailService mailService;

    @Autowired
    private IndirizzoRepository indirizzoRp;

    @Autowired
    private ProvinciaRepository provinciaRp;

    @Autowired
    private ComuneRepository comuneRp;

    public Page<Cliente> getAll(Pageable pageable) {
        return clienteRp.findAll(pageable).map(c -> {
            c.getSede().getFirst().setNomeComune(c.getSede().getFirst().getComune().getNome());
            c.getSede().get(1).setNomeComune(c.getSede().getFirst().getComune().getNome());
            c.getSede().getFirst().setSiglaProvincia(c.getSede().getFirst().getComune().getProvincia().getSigla());
            c.getSede().get(1).setSiglaProvincia(c.getSede().get(1).getComune().getProvincia().getSigla());
            return c;
        });

    }

    public Cliente getById(UUID id) throws NotFoundException {
        Cliente c = clienteRp.findById(id)
                .orElseThrow(() -> new NotFoundException("Il cliente con id " + id + " è inesistente"));
        c.getSede().getFirst().setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().get(1).setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().getFirst().setSiglaProvincia(c.getSede().getFirst().getComune().getProvincia().getSigla());
        c.getSede().get(1).setSiglaProvincia(c.getSede().get(1).getComune().getProvincia().getSigla());
        return c;
    }

    public Cliente updateAfterUpload(Cliente cliente, String url) {
        cliente.setLogoAziendale(url);
        cliente.getSede().getFirst().setNomeComune(cliente.getSede().getFirst().getComune().getNome());
        cliente.getSede().get(1).setNomeComune(cliente.getSede().getFirst().getComune().getNome());
        cliente.getSede().getFirst().setSiglaProvincia(cliente.getSede().getFirst().getComune().getProvincia().getSigla());
        cliente.getSede().get(1).setSiglaProvincia(cliente.getSede().get(1).getComune().getProvincia().getSigla());
        return clienteRp.save(cliente);
    }

    public Cliente save(ClienteDTO cliente) throws BadRequestException, InternalServerErrorException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException {
        logger.info("start");
        Cliente c = new Cliente();
        c.setRagioneSociale(cliente.ragioneSociale());
        c.setPartitaIva(cliente.partitaIva());
        c.setEmail(cliente.email());
        c.setDataUltimoContatto(LocalDate.parse(cliente.dataUltimoContatto()));
        c.setFatturatoAnnuale(cliente.fatturatoAnnuale());
        c.setPec(cliente.pec());
        c.setTelefono(cliente.telefono());
        c.setEmailContatto(cliente.emailContatto());
        c.setNomeContatto(cliente.nomeContatto());
        c.setCognomeContatto(cliente.cognomeContatto());
        c.setTelefonoContatto(cliente.telefonoContatto());
        c.getSede().getFirst().setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().get(1).setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().getFirst().setSiglaProvincia(c.getSede().getFirst().getComune().getProvincia().getSigla());
        c.getSede().get(1).setSiglaProvincia(c.getSede().get(1).getComune().getProvincia().getSigla());
        logger.info("set almost complete");
        try {
            c.setTipoCliente(TipoCliente.valueOf(cliente.tipoCliente()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("tipoCliente non valido, sono ammessi solo i seguenti valori esatti: " +
                    "PA, SAS, SNC, SS, SRL, SPA, SAPA, COOPERATIVA");
        }
        logger.info("enum ok");

        Comune comuneSedeLegale = comuneRp.findByNomeAndProvincia(cliente.sedeLegale().nomeComune(), cliente.sedeLegale().siglaProvincia())
                .orElseThrow(
                        () -> new BadRequestException("Comune sede legale non trovato")
                );
        logger.info("sede legale");

        Comune comuneSedeOperativa = comuneRp.findByNomeAndProvincia(cliente.sedeOperativa().nomeComune(), cliente.sedeOperativa().siglaProvincia())
                .orElseThrow(
                        () -> new BadRequestException("Comune sede operativa non trovato")
                );
        try {
            logger.info("trying to save");
            clienteRp.save(c);
        } catch (DataIntegrityViolationException e) {
            if (clienteRp.getAllEmails().contains(c.getEmail()))
                throw new BadRequestException("L'email inserita è già esistente, impossibile creare");
            if (clienteRp.getAllPecs().contains(c.getPec()))
                throw new BadRequestException("La PEC inserita è già esistente, impossibile creare");
            throw new InternalServerErrorException("Errore di violazione dell'integrità dei dati. " + e.getMessage());
        }

        Indirizzo indirizzoSedeLegale = new Indirizzo(cliente.sedeLegale().via(), cliente.sedeLegale().civico(),
                cliente.sedeLegale().localita(), cliente.sedeLegale().cap(), comuneSedeLegale, c, TipoSede.SEDE_LEGALE);

        Indirizzo indirizzoSedeOperativa = new Indirizzo(cliente.sedeOperativa().via(), cliente.sedeOperativa().civico(),
                cliente.sedeOperativa().localita(), cliente.sedeOperativa().cap(), comuneSedeOperativa, c, TipoSede.SEDE_OPERATIVA);
        logger.info("indirizzi");
        indirizzoRp.save(indirizzoSedeLegale);
        indirizzoRp.save(indirizzoSedeOperativa);



        mailService.sendEmail(
                c.getEmail(),
                "ISCRIZIONE EFFETTUATA CON SUCCESSO",
                "Benvenuto" + c.getNomeContatto() + ", ti diamo il benvenuto in Epic Energy Services"
        );

        return c;
    }

    public Cliente update(UUID id, ClienteDTO cliente) throws NotFoundException, BadRequestException, InternalServerErrorException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException {
        Cliente c = getById(id);
        c.setRagioneSociale(cliente.ragioneSociale());
        c.setPartitaIva(cliente.partitaIva());
        c.setEmail(cliente.email());
        c.setDataUltimoContatto(LocalDate.parse(cliente.dataUltimoContatto()));
        c.setFatturatoAnnuale(cliente.fatturatoAnnuale());
        c.setPec(cliente.pec());
        c.setTelefono(cliente.telefono());
        c.setEmailContatto(cliente.emailContatto());
        c.setNomeContatto(cliente.nomeContatto());
        c.setCognomeContatto(cliente.cognomeContatto());
        c.setTelefonoContatto(cliente.telefonoContatto());
        c.getSede().getFirst().setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().get(1).setNomeComune(c.getSede().getFirst().getComune().getNome());
        c.getSede().getFirst().setSiglaProvincia(c.getSede().getFirst().getComune().getProvincia().getSigla());
        c.getSede().get(1).setSiglaProvincia(c.getSede().get(1).getComune().getProvincia().getSigla());
        try {
            c.setTipoCliente(TipoCliente.valueOf(cliente.tipoCliente()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("tipoCliente non valido, sono ammessi solo i seguenti valori esatti: " +
                    "PA, SAS, SNC, SS, SRL, SPA, SAPA, COOPERATIVA");
        }
        Comune comuneSedeLegale = comuneRp.findByNomeAndProvincia(cliente.sedeLegale().nomeComune(), cliente.sedeLegale().siglaProvincia())
                .orElseThrow(
                        () -> new it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException("Comune sede legale non trovato")
                );


        Comune comuneSedeOperativa = comuneRp.findByNomeAndProvincia(cliente.sedeLegale().nomeComune(), cliente.sedeOperativa().siglaProvincia())
                .orElseThrow(
                        () -> new it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException("Comune sede operativa non trovato")
                );


        try {
            clienteRp.save(c);
        } catch (DataIntegrityViolationException e) {
            if (clienteRp.getAllEmails().contains(c.getEmail()) || clienteRp.getAllPecs().contains(c.getPec()))
                throw new BadRequestException("L'email e/o la PEC inseriti sono già esistenti, impossibile aggiornare");
            throw new InternalServerErrorException("Errore di violazione dell'integrità dei dati. " + e.getMessage());
        }

        Indirizzo indirizzoSedeLegale = new Indirizzo(cliente.sedeLegale().via(), cliente.sedeLegale().civico(),
                cliente.sedeLegale().localita(), cliente.sedeLegale().cap(), comuneSedeLegale, c, TipoSede.SEDE_LEGALE);

        Indirizzo indirizzoSedeOperativa = new Indirizzo(cliente.sedeOperativa().via(), cliente.sedeOperativa().civico(),
                cliente.sedeOperativa().localita(), cliente.sedeOperativa().cap(), comuneSedeOperativa, c, TipoSede.SEDE_OPERATIVA);

        indirizzoRp.save(indirizzoSedeLegale);
        indirizzoRp.save(indirizzoSedeOperativa);





        mailService.sendEmail(
                c.getEmail(),
                "MODIFICA EFFETTUATA CON SUCCESSO",
                "Ciao" + c.getNomeContatto() + ", il tuo profilo cliente con id" + id + "è stato modificato"
        );
        return c;
    }

    public void delete(UUID id) throws NotFoundException {
        Cliente c = getById(id);
        Indirizzo sedeOperativa = c.getSede().stream().filter(i -> i.getTipoSede().equals(TipoSede.SEDE_OPERATIVA)).findFirst().get();
        Indirizzo sedeLegale = c.getSede().stream().filter(i -> i.getTipoSede().equals(TipoSede.SEDE_LEGALE)).findFirst().get();
        indirizzoRp.deleteAllById(List.of(sedeLegale.getId(), sedeOperativa.getId()));
        clienteRp.delete(c);
    }


}
