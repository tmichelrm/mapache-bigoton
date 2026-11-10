package mx.edu.caidt.barberia_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * La cita guarda solo los ids del cliente, barbero y servicio (igual que la tabla).
 * El frontend busca los nombres con las otras tres rutas.
 */
@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cita")
    private Long idCita;

    @NotNull(message = "El cliente es obligatorio.")
    @Column(name = "id_Cliente")
    private Long idCliente;

    @NotNull(message = "El barbero es obligatorio.")
    @Column(name = "id_Barbero")
    private Long idBarbero;

    @NotNull(message = "El servicio es obligatorio.")
    @Column(name = "id_Servicio")
    private Long idServicio;

    @NotNull(message = "La fecha y hora de inicio son obligatorias.")
    @Column(name = "inicio")
    private LocalDateTime inicio;

    // El backend lo recalcula: inicio + duración del servicio
    @Column(name = "fin")
    private LocalDateTime fin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdBarbero() { return idBarbero; }
    public void setIdBarbero(Long idBarbero) { this.idBarbero = idBarbero; }

    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
}
