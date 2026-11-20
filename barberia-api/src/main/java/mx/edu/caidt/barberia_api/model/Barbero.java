package mx.edu.caidt.barberia_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "barbero")
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Barbero")
    private Long idBarbero;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre admite hasta 50 caracteres.")
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 30, message = "La especialidad admite hasta 30 caracteres.")
    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "activo")
    private Boolean activo = Boolean.TRUE;

    @Column(name = "id_Sucursal")
    private Long idSucursal;

    public Long getIdBarbero() { return idBarbero; }
    public void setIdBarbero(Long idBarbero) { this.idBarbero = idBarbero; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Long getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Long idSucursal) { this.idSucursal = idSucursal; }
}
