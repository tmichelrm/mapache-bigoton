package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.model.Cita;
import mx.edu.caidt.barberia_api.model.EstadoCita;
import mx.edu.caidt.barberia_api.model.Servicio;
import mx.edu.caidt.barberia_api.repository.BarberoRepository;
import mx.edu.caidt.barberia_api.repository.CitaRepository;
import mx.edu.caidt.barberia_api.repository.ClienteRepository;
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
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaRepository citas;
    private final ClienteRepository clientes;
    private final BarberoRepository barberos;
    private final ServicioRepository servicios;

    public CitaController(CitaRepository citas,
                          ClienteRepository clientes,
                          BarberoRepository barberos,
                          ServicioRepository servicios) {
        this.citas = citas;
        this.clientes = clientes;
        this.barberos = barberos;
        this.servicios = servicios;
    }

    @GetMapping
    public List<Cita> listar() {
        return citas.findAll(Sort.by("inicio"));
    }

    @GetMapping("/{id}")
    public Cita obtener(@PathVariable Long id) {
        return citas.findById(id).orElseThrow(this::noEncontrada);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cita crear(@Valid @RequestBody Cita cita) {
        cita.setIdCita(null);
        return guardar(cita, -1L);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable Long id, @Valid @RequestBody Cita cita) {
        if (!citas.existsById(id)) {
            throw noEncontrada();
        }
        cita.setIdCita(id);
        return guardar(cita, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        if (!citas.existsById(id)) {
            throw noEncontrada();
        }
        citas.deleteById(id);
    }

    /** Reglas comunes de crear y editar. idExcluir = la cita que se edita (-1 si es nueva). */
    private Cita guardar(Cita cita, Long idExcluir) {
        if (!clientes.existsById(cita.getIdCliente())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe.");
        }
        if (!barberos.existsById(cita.getIdBarbero())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El barbero no existe.");
        }
        Servicio servicio = servicios.findById(cita.getIdServicio()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El servicio no existe."));

        if (cita.getEstado() == null) {
            cita.setEstado(EstadoCita.PROGRAMADA);
        }

        // El fin siempre se calcula: inicio + duración del servicio
        cita.setFin(cita.getInicio().plusMinutes(servicio.getDuracionMinutos()));

        if (cita.getEstado() != EstadoCita.CANCELADA) {
            long empalmes = citas.contarEmpalmes(
                    cita.getIdBarbero(), cita.getInicio(), cita.getFin(), idExcluir, EstadoCita.CANCELADA);
            if (empalmes > 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "El barbero ya tiene una cita que se empalma con ese horario.");
            }
        }
        return citas.save(cita);
    }

    private ResponseStatusException noEncontrada() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "La cita no existe.");
    }
}
