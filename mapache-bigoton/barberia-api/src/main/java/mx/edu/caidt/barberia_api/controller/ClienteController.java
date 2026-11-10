package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.model.Cliente;
import mx.edu.caidt.barberia_api.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repositorio;

    public ClienteController(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repositorio.findAll(Sort.by("idCliente"));
    }

    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(this::noEncontrado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@Valid @RequestBody Cliente cliente) {
        cliente.setIdCliente(null); // el id lo asigna la base de datos
        return repositorio.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        if (!repositorio.existsById(id)) {
            throw noEncontrado();
        }
        cliente.setIdCliente(id);
        return repositorio.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            throw noEncontrado();
        }
        repositorio.deleteById(id);
    }

    private ResponseStatusException noEncontrado() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no existe.");
    }
}
