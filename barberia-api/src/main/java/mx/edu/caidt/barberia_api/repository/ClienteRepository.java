package mx.edu.caidt.barberia_api.repository;

import mx.edu.caidt.barberia_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
