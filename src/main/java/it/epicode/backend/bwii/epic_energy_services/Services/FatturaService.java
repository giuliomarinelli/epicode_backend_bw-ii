package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.FatturaDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Models.enums.StatoFattura;
import it.epicode.backend.bwii.epic_energy_services.repositories.FatturaRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private ClienteService clienteSvc;

    public Page<Fattura> getAllFacture(Pageable pageable) {
        return fatturaRepository.findAll(pageable).map(f -> {
            f.setClienteId(f.getCliente().getId());
            return f;
        });
    }

    public Fattura getFactureForId(int numero) throws NotFoundException {
        Fattura fattura = fatturaRepository.findById(numero).orElseThrow(
                () -> new NotFoundException("fatttura nr. " + numero + " non trovata")
        );
        fattura.setClienteId(fattura.getCliente().getId());
        return fattura;
    }

    public Fattura saveFacture(FatturaDTO fatturaDTO) throws NotFoundException {

        Fattura fattura = new Fattura(fatturaDTO.data(), fatturaDTO.importo(),
                clienteSvc.getById(fatturaDTO.clienteId()), fatturaDTO.stato());
        fattura.setClienteId(fatturaDTO.clienteId());
        return fatturaRepository.save(fattura);
    }

    public Fattura updateFacture(int numero, FatturaDTO fatturaDTO) throws NotFoundException, BadRequestException {
        Fattura fattura = getFactureForId(numero);
        Cliente cliente = clienteSvc.getById(fatturaDTO.clienteId());
        fattura.setCliente(cliente);
        fattura.setData(fatturaDTO.data());
        fattura.setImporto(fatturaDTO.importo());
        fattura.setStato(fatturaDTO.stato());
        fattura.setClienteId(fattura.getCliente().getId());
        return fatturaRepository.save(fattura);
    }

    public void deleteFacture(int numero) throws NotFoundException {
        Fattura fattura = getFactureForId(numero);
        fatturaRepository.delete(fattura);

    }

    public Page<Fattura> findByStato(StatoFattura stato, Pageable pageable) {
        return fatturaRepository.findByStatoOrderByDataAsc(stato, pageable);
    }

    public Page<Fattura> findByData(LocalDate data, Pageable pageable) {
        return fatturaRepository.findByData(data, pageable);
    }

    public Page<Fattura> findByAnno(int anno, Pageable pageable) {
        return fatturaRepository.findByAnnoOrderByDataAsc(anno, pageable);
    }

    public Page<Fattura> findByImportoBetween(double minImporto, double maxImporto, Pageable pageable) {
        return fatturaRepository.findByImportoBetweenOrderByDataAsc(minImporto, maxImporto, pageable);
    }

}
