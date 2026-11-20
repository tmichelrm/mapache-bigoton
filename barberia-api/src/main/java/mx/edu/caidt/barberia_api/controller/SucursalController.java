package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.model.Sucursal;
import mx.edu.caidt.barberia_api.repository.SucursalRepository;
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
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalRepository repositorio;

    public SucursalController(SucursalRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Sucursal> listar() {
        return repositorio.findAll(Sort.by("idSucursal"));
    }

    @GetMapping("/{id}")
    public Sucursal obtener(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(this::noEncontrado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sucursal crear(@Valid @RequestBody Sucursal sucursal) {
        sucursal.setIdSucursal(null);
        return repositorio.save(sucursal);
    }

    @PutMapping("/{id}")
    public Sucursal actualizar(@PathVariable Long id, @Valid @RequestBody Sucursal sucursal) {
        if (!repositorio.existsById(id)) {
            throw noEncontrado();
        }
        sucursal.setIdSucursal(id);
        return repositorio.save(sucursal);
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
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no existe.");
    }
}
