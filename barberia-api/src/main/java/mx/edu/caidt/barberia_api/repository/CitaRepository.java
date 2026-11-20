package mx.edu.caidt.barberia_api.repository;

import mx.edu.caidt.barberia_api.model.Cita;
import mx.edu.caidt.barberia_api.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("select count(c) from Cita c "
            + "where c.idBarbero = :idBarbero "
            + "and c.estado <> :cancelada "
            + "and c.inicio < :fin "
            + "and c.fin > :inicio "
            + "and c.idCita <> :excluir")
    long contarEmpalmes(@Param("idBarbero") Long idBarbero,
                        @Param("inicio") LocalDateTime inicio,
                        @Param("fin") LocalDateTime fin,
                        @Param("excluir") Long excluir,
                        @Param("cancelada") EstadoCita cancelada);
}
