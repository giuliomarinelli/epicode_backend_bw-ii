package it.epicode.backend.bwii.epic_energy_services.Controllers;

import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Services.ClienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

        @Autowired
        private ClienteService clienteSv;

    @GetMapping("")
    public Page<Cliente> getAllCLiente(Pageable pageable){
        return clienteSv.getAll(pageable);
    }
    @GetMapping("/id")
    public Cliente getById(@RequestParam UUID id)throws NotFoundException {
        return clienteSv.getById(id);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente saveCliente(@RequestBody ClienteDTO cliente, BindingResult bindingResult) throws NotFoundException {
        HandlerException.notFoundException(bindingResult);
        return clienteSv.save(cliente);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente updateCliente(@PathVariable UUID id, @RequestBody ClienteDTO cliente, BindingResult bindingResult)throws NotFoundException{
        HandlerException.notFoundException(bindingResult);
        return clienteSv.update(id, cliente);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCliente(@PathVariable UUID id)throws NotFoundException{
        clienteSv.delete(id);
    }
}
