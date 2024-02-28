package it.epicode.backend.bwii.epic_energy_services.Controllers;

import com.cloudinary.Cloudinary;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.HandlerException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Utente;
import it.epicode.backend.bwii.epic_energy_services.Services.ClienteService;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteSv;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("")
    public Page<Cliente> getAllCLiente(Pageable pageable) {
        return clienteSv.getAll(pageable);
    }

    @GetMapping("/id")
    public Cliente getById(@RequestParam UUID id) throws NotFoundException {
        return clienteSv.getById(id);
    }

    @PatchMapping("/{id}/upload-logo-aziendale")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente upload(@RequestParam("file") MultipartFile file, @PathVariable UUID id) throws IOException, UnauthorizedException, NotFoundException {

        Cliente cliente = clienteSv.getById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url");
        return clienteSv.updateAfterUpload(cliente, url);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente saveCliente(@RequestBody ClienteDTO cliente, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, org.apache.coyote.BadRequestException {
        HandlerException.notFoundException(bindingResult);
        return clienteSv.save(cliente);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente updateCliente(@PathVariable UUID id, @RequestBody ClienteDTO cliente, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException, it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException, org.apache.coyote.BadRequestException {
        HandlerException.notFoundException(bindingResult);
        return clienteSv.update(id, cliente);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCliente(@PathVariable UUID id) throws NotFoundException {
        clienteSv.delete(id);
    }
}
