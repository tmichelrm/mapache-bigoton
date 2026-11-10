package mx.edu.caidt.barberia_api.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/** Convierte los errores en respuestas JSON claras: { "mensaje": "..." } */
@RestControllerAdvice
public class ManejadorErrores {

    /** Teléfono repetido, o borrar algo que tiene citas asociadas. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> integridad(DataIntegrityViolationException ex) {
        String detalle = String.valueOf(ex.getMostSpecificCause().getMessage());
        String mensaje;
        if (detalle.contains("Duplicate entry")) {
            mensaje = "Ya existe un registro con ese dato (por ejemplo, el teléfono repetido).";
        } else if (detalle.contains("foreign key")) {
            mensaje = "No se puede completar: el registro tiene citas asociadas.";
        } else {
            mensaje = "No se puede completar la operación por restricciones de la base de datos.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", mensaje));
    }

    /** Datos que no cumplen las validaciones (@NotBlank, @Size, ...). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensaje", mensaje));
    }

    /** 404, 400 y 409 que lanzan los controladores. */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> estado(ResponseStatusException ex) {
        String mensaje = ex.getReason() != null ? ex.getReason() : "Error.";
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("mensaje", mensaje));
    }
}
