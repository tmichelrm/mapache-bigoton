package mx.edu.caidt.barberia_api.repository;

import mx.edu.caidt.barberia_api.model.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {
}
