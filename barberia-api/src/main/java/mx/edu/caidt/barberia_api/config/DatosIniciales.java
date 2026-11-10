package mx.edu.caidt.barberia_api.config;

import mx.edu.caidt.barberia_api.model.Usuario;
import mx.edu.caidt.barberia_api.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Al arrancar, si no hay ningún usuario, crea el administrador para poder iniciar sesión. */
@Component
public class DatosIniciales implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatosIniciales.class);

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final String usuario;
    private final String clave;
    private final String nombre;

    public DatosIniciales(UsuarioRepository usuarios,
                          PasswordEncoder encoder,
                          @Value("${app.admin.usuario:mariana}") String usuario,
                          @Value("${app.admin.clave:Mapache2026}") String clave,
                          @Value("${app.admin.nombre:Mariana López}") String nombre) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
    }

    @Override
    public void run(String... args) {
        try {
            if (usuarios.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre(nombre);
                admin.setUsuario(usuario);
                admin.setClaveHash(encoder.encode(clave));
                admin.setRol("Administradora");
                usuarios.save(admin);
                log.info("Usuario inicial creado: '{}'", usuario);
            }
        } catch (DataAccessException e) {
            log.error("No se pudo revisar la tabla 'usuario'. ¿Ya ejecutaste sql/01_base_de_datos.sql? "
                    + "Sin esa tabla no funcionará el inicio de sesión.", e);
        }
    }
}
