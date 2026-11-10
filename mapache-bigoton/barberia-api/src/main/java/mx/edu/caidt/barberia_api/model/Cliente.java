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
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cliente")
    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre admite hasta 50 caracteres.")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 15, message = "El teléfono admite hasta 15 caracteres.")
    @Column(name = "telefono")
    private String telefono;

    @Size(max = 100, message = "El correo admite hasta 100 caracteres.")
    @Column(name = "email")
    private String email;

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
