package mx.edu.caidt.barberia_api.controller;

import mx.edu.caidt.barberia_api.config.TokenService;
import mx.edu.caidt.barberia_api.model.Usuario;
import mx.edu.caidt.barberia_api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    public record LoginRequest(String usuario, String clave) {
    }

    public record LoginResponse(String token, String nombre, String rol) {
    }

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final TokenService tokens;

    public AuthController(UsuarioRepository usuarios, PasswordEncoder encoder, TokenService tokens) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.tokens = tokens;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest datos) {
        if (datos == null || datos.usuario() == null || datos.clave() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Usuario> encontrado = usuarios.findByUsuario(datos.usuario());
        if (encontrado.isEmpty() || !encoder.matches(datos.clave(), encontrado.get().getClaveHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario u = encontrado.get();
        String token = tokens.crear(u.getIdUsuario());
        return ResponseEntity.ok(new LoginResponse(token, u.getNombre(), u.getRol()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String cabecera) {
        tokens.cerrar(TokenService.extraer(cabecera));
        return ResponseEntity.noContent().build();
    }
}
