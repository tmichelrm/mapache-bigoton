package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.model.Servicio;
import mx.edu.caidt.barberia_api.repository.ServicioRepository;
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
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioRepository repositorio;

    public ServicioController(ServicioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Servicio> listar() {
        return repositorio.findAll(Sort.by("idServicio"));
    }

    @GetMapping("/{id}")
    public Servicio obtener(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(this::noEncontrado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servicio crear(@Valid @RequestBody Servicio servicio) {
        servicio.setIdServicio(null);
        return repositorio.save(servicio);
    }

    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id, @Valid @RequestBody Servicio servicio) {
        if (!repositorio.existsById(id)) {
            throw noEncontrado();
        }
        servicio.setIdServicio(id);
        return repositorio.save(servicio);
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
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "El servicio no existe.");
    }
}
