package mx.edu.caidt.barberia_api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/** Revisa el token antes de cada petición a /api/** (excepto el login). */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokens;

    public AuthInterceptor(TokenService tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean preHandle(HttpServletRequest peticion, HttpServletResponse respuesta, Object handler) {
        // Antes de cada petición, el navegador manda una pregunta previa (OPTIONS): se deja pasar.
        if ("OPTIONS".equalsIgnoreCase(peticion.getMethod())) {
            return true;
        }

        String token = TokenService.extraer(peticion.getHeader("Authorization"));
        if (tokens.esValido(token)) {
            return true;
        }

        respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
