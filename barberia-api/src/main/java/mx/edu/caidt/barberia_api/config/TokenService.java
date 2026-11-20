package mx.edu.caidt.barberia_api.config;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TokenService {

    private static final long HORAS_DE_VIDA = 8;

    private final Map<String, Instant> sesiones = new ConcurrentHashMap<>();

    public String crear(Long idUsuario) {
        String token = UUID.randomUUID().toString();
        sesiones.put(token, Instant.now().plusSeconds(HORAS_DE_VIDA * 3600));
        return token;
    }

    public boolean esValido(String token) {
        if (token == null) {
            return false;
        }
        Instant vence = sesiones.get(token);
        if (vence == null) {
            return false;
        }
        if (vence.isBefore(Instant.now())) {
            sesiones.remove(token);
            return false;
        }
        return true;
    }

    public void cerrar(String token) {
        if (token != null) {
            sesiones.remove(token);
        }
    }

    public static String extraer(String cabecera) {
        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            return cabecera.substring(7);
        }
        return null;
    }
}
