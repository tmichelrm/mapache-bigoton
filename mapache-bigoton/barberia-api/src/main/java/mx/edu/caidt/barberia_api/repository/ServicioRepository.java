package mx.edu.caidt.barberia_api.repository;

import mx.edu.caidt.barberia_api.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
