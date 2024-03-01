package it.epicode.backend.bwii.epic_energy_services;
import com.cloudinary.Cloudinary;
import it.epicode.backend.bwii.epic_energy_services.Controllers.ClienteController;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.BadRequestException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.InternalServerErrorException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.NotFoundException;
import it.epicode.backend.bwii.epic_energy_services.Exceptions.UnauthorizedException;
import it.epicode.backend.bwii.epic_energy_services.Models.RequestDTO.ClienteDTO;
import it.epicode.backend.bwii.epic_energy_services.Models.entities.Cliente;
import it.epicode.backend.bwii.epic_energy_services.Services.ClienteService;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

    @Mock
    ClienteService clienteService;

    @Mock
    Cloudinary cloudinary;

    @Mock
    static AnnotationConfigApplicationContext ctx;
    @InjectMocks
    private ClienteController clienteController;
    @BeforeAll
    static void setUp() {
        ctx= new AnnotationConfigApplicationContext();
        MockitoAnnotations.openMocks(ClienteController.class);
    }
    @Test
     void testGetAllCLiente() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> page = new PageImpl<>(Arrays.asList(new Cliente(), new Cliente()));

        Mockito.when(clienteService.getAll(pageable)).thenReturn(page);


    }

    @Test
     void testGetById() throws NotFoundException {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();

        Mockito.when(clienteService.getById(id)).thenReturn(cliente);

        Cliente result = clienteController.getById(id);

        assertEquals(cliente, result);
    }

    @Test
     void testUpload() throws IOException, UnauthorizedException, NotFoundException {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        MultipartFile file = Mockito.mock(MultipartFile.class);
        String url = "https://example.com/image.jpg";

        Mockito.when(clienteService.getById(id)).thenReturn(cliente);
        Mockito.when(cloudinary.uploader().upload(Mockito.any(byte[].class), Mockito.anyMap())).thenReturn(singletonMap("url", url));

        Cliente result = clienteController.upload(file, id);

        assertEquals(url, result.getLogoAziendale());
    }
//
//    @Test
//    public void testSaveCliente() throws BadRequestException, NotFoundException {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        Cliente cliente = new Cliente();
//
//        Mockito.when(clienteService.save(clienteDTO)).thenReturn(cliente);
//
//        Cliente result = clienteController.saveCliente(clienteDTO);
//
//        assertEquals(cliente, result);
//    }

//    @Test
//    public void testUpdateCliente() throws BadRequestException, NotFoundException, org.apache.coyote.BadRequestException, InternalServerErrorException {
//        UUID id = UUID.randomUUID();
//        ClienteDTO clienteDTO = new ClienteDTO();
//        Cliente cliente = new Cliente();
//
//        Mockito.when(clienteService.update(id, clienteDTO)).thenReturn(cliente);
//
//        Cliente result = clienteController.updateCliente(id, clienteDTO, null);
//
//        assertEquals(cliente, result);
//    }

    @Test
    public void testDeleteCliente() throws NotFoundException {
        UUID id = UUID.randomUUID();

        clienteController.deleteCliente(id);

        Mockito.verify(clienteService).delete(id);
    }


}
