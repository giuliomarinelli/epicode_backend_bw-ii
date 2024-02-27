package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.FatturaDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Fattura;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.repositories.FatturaRepository;
import it.epicode.backend.bwii.epic_energy_services.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FatturaService {
    @Autowired
   private FatturaRepository fatturaRepository;
@Autowired
private UtenteService utenteService;
    public Page<Fattura>getAllFacture(Pageable pageable){
        return  fatturaRepository.findAll(pageable);
    }

    public  Fattura getFactureForId(int numero)throws NotFoundException {
        return fatturaRepository.findById(numero).orElseThrow(()->new NotFoundException("fatttura nr :" + numero + "non trovata" ));
    }
    public  Fattura saveFacture(FatturaDTO fatturaDTO) throws  NotFoundException{
        Fattura fattura =new Fattura(fatturaDTO.data(),fatturaDTO.importo(),fatturaDTO.cliente());
        return fatturaRepository.save(fattura);
    }

    public Fattura updateFacture(int numero, FatturaDTO fatturaDTO)throws NotFoundException{
        Fattura fattura=getFactureForId(numero);
        Utente utente= utenteService.getUtenteById(fatturaDTO.cliente().getId());
        fattura.setCliente(fatturaDTO.cliente());
        fattura.setData(fatturaDTO.data());
        fattura.setImporto(fatturaDTO.importo());
        fattura.setData(fatturaDTO.data());
        return fatturaRepository.save(fattura);
    }

public  void deleteFacture(int numero){
        Fattura fattura=getFactureForId(numero);
        fatturaRepository.delete(fattura);

}
}
