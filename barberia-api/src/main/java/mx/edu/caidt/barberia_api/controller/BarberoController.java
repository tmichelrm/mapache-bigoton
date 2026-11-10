package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.model.Barbero;
import mx.edu.caidt.barberia_api.repository.BarberoRepository;
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
@RequestMapping("/api/barberos")
public class BarberoController {

    private final BarberoRepository repositorio;

    public BarberoController(BarberoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Barbero> listar() {
        return repositorio.findAll(Sort.by("idBarbero"));
    }

    @GetMapping("/{id}")
    public Barbero obtener(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(this::noEncontrado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Barbero crear(@Valid @RequestBody Barbero barbero) {
        barbero.setIdBarbero(null);
        if (barbero.getActivo() == null) {
            barbero.setActivo(Boolean.TRUE);
        }
        return repositorio.save(barbero);
    }

    @PutMapping("/{id}")
    public Barbero actualizar(@PathVariable Long id, @Valid @RequestBody Barbero barbero) {
        if (!repositorio.existsById(id)) {
            throw noEncontrado();
        }
        barbero.setIdBarbero(id);
        if (barbero.getActivo() == null) {
            barbero.setActivo(Boolean.TRUE);
        }
        return repositorio.save(barbero);
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
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "El barbero no existe.");
    }
}
