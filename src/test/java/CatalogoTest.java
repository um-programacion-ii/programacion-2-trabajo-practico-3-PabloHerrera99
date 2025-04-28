import enums.Estado;
import gestores.Catalogo;
import modelos.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {
    private Catalogo catalogo;
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
        libro1 = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro2 = new Libro("978-0-13-235088-4", "Clean Architecture", "Robert C. Martin");
        catalogo.agregarLibro(libro1);
        catalogo.agregarLibro(libro2);
    }

    @Test
    void testBuscarPorIsbnExistente() {
        Libro libro = catalogo.buscarLibro("978-3-16-148410-0");
        assertNotNull(libro);
        assertEquals("Clean Code", libro.getTitulo());
    }

    @Test
    void testBuscarPorIsbnInexistente() {
        Libro libro = catalogo.buscarLibro("978-3-16-148410-1");
        assertNull(libro);
    }

    @Test
    void testListarDisponibles() {
        libro1.setEstado(Estado.PRESTADO);
        List<Libro> disponibles = catalogo.getLibrosDisponibles();

        assertEquals(1, disponibles.size());
        assertEquals("978-0-13-235088-4", disponibles.get(0).getIsbn());
    }
}