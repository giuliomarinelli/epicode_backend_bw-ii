package it.epicode.backend.bwii.epic_energy_services.Services;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.IndirizzoDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Comune;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Indirizzo;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.repositories.IndirizzoRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private  UtenteService utenteService;

    public Page<Indirizzo> getAllAddresses(Pageable pageable) {
        return indirizzoRepository.findAll(pageable);
    }

    public Indirizzo getAdressForId(UUID id)throws NotFoundException {
        return indirizzoRepository.findById(id).orElseThrow(()->new NotFoundException("No such address with : " + id));
    }

    public  Indirizzo saveAddress(IndirizzoDTO indirizzoDTO)throws NotFoundException {
        Indirizzo indirizzo =new Indirizzo(indirizzoDTO.via(),indirizzoDTO.civico(),indirizzoDTO.localita(),indirizzoDTO.cap(),indirizzoDTO.comune(),indirizzoDTO.cliente());
    return  indirizzoRepository.save(indirizzo);
    }
    public  Indirizzo updateAddress(UUID id, IndirizzoDTO indirizzoDTO) throws NotFoundException, BadRequestException {
        Indirizzo indirizzo=getAdressForId(id);
        Utente utente= utenteService.getById(id);
        //Comune comune= comuneService.getComuneById(id);
        indirizzo.setComune(indirizzoDTO.comune());
        indirizzo.setCap(indirizzoDTO.cap());
        indirizzo.setVia(indirizzoDTO.via());
        indirizzo.setCivico(indirizzoDTO.civico());
        indirizzo.setCliente(indirizzoDTO.cliente());
        return  indirizzoRepository.save(indirizzo);
    }

    public  void  deleteAddress(UUID id)throws NotFoundException{
        Indirizzo indirizzo=getAdressForId(id);
        indirizzoRepository.delete(indirizzo);
    }
}