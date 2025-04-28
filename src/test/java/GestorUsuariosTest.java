import gestores.Catalogo;
import gestores.GestorUsuarios;
import gestores.SistemaPrestamos;
import modelos.Libro;
import modelos.Prestamo;
import modelos.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GestorUsuariosTest {

    @Mock
    private Catalogo catalogo;

    @Mock
    private SistemaPrestamos sistemaPrestamos;

    @InjectMocks
    private GestorUsuarios gestorUsuarios;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gestorUsuarios = new GestorUsuarios(catalogo, sistemaPrestamos);
    }

    @Test
    void testRegistrarUsuario() {
        gestorUsuarios.registrarUsuario("usuario1");

        assertEquals(gestorUsuarios.buscarUsuario("usuario1").getNombre(), "usuario1");
    }

    @Test
    void testRegistrarUsuarioExistente() {
        gestorUsuarios.registrarUsuario("usuario1");

        assertThrows(IllegalArgumentException.class, () -> {
            gestorUsuarios.registrarUsuario("usuario1");
        });
    }

    @Test
    void testRegistrarPrestamo() {
        gestorUsuarios.registrarUsuario("usuario1");
        Usuario usuario = gestorUsuarios.buscarUsuario("usuario1");
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        when(catalogo.buscarLibro("978-3-16-148410-0")).thenReturn(libro);
        when(sistemaPrestamos.prestarLibro("978-3-16-148410-0"))
                .thenReturn(new Prestamo(libro));

        gestorUsuarios.registrarPrestamo("usuario1", "978-3-16-148410-0");

        verify(sistemaPrestamos).prestarLibro("978-3-16-148410-0");
        assertEquals(1, usuario.getHistorialPrestamos().size());
    }

    @Test
    void testRegistrarPrestamoUsuarioNoEncontrado() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestorUsuarios.registrarPrestamo("usuarioInexistente", "978-3-16-148410-0");
        });
    }

    @Test
    void testRegistrarPrestamoLibroNoEncontrado() {
        gestorUsuarios.registrarUsuario("usuario1");

        assertThrows(IllegalArgumentException.class, () -> {
            gestorUsuarios.registrarPrestamo("usuario1", "000");
        });
    }
}
