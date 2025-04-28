import enums.Estado;
import gestores.Catalogo;
import gestores.SistemaPrestamos;
import modelos.Libro;
import modelos.Prestamo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SistemaPrestamosTest {
    @Mock
    private Catalogo catalogo;

    @InjectMocks
    private SistemaPrestamos sistemaPrestamos;

    @Test
    void testPrestarLibro() {
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        when(catalogo.buscarLibro("978-3-16-148410-0")).thenReturn(libro);

        Prestamo prestamo = sistemaPrestamos.prestarLibro("978-3-16-148410-0");

        assertNotNull(prestamo);
        verify(catalogo).buscarLibro("978-3-16-148410-0");
        assertEquals(Estado.PRESTADO, libro.getEstado());
    }

    @Test
    void testPrestarLibroInexitente() {
        when(catalogo.buscarLibro("000")).thenReturn(null);

        Prestamo prestamo = sistemaPrestamos.prestarLibro("000");

        assertNull(prestamo);
        verify(catalogo).buscarLibro("000");
    }

    @Test
    void testPrestarLibroNoDisponible() {
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.setEstado(Estado.PRESTADO); // Ya está prestado
        when(catalogo.buscarLibro("978-3-16-148410-0")).thenReturn(libro);

        Prestamo prestamo = sistemaPrestamos.prestarLibro("978-3-16-148410-0");

        assertNull(prestamo);
        verify(catalogo).buscarLibro("978-3-16-148410-0");
    }

    @Test
    void testDevolverLibroExitoso() {
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.setEstado(Estado.PRESTADO); // El libro está prestado
        when(catalogo.buscarLibro("978-3-16-148410-0")).thenReturn(libro);

        boolean resultado = sistemaPrestamos.devolverLibro("978-3-16-148410-0");

        assertTrue(resultado);
        verify(catalogo).buscarLibro("978-3-16-148410-0");
        assertEquals(Estado.DISPONIBLE, libro.getEstado());
    }

    @Test
    void testDevolverLibroNoPrestado() {
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.setEstado(Estado.DISPONIBLE); // Ya disponible
        when(catalogo.buscarLibro("978-3-16-148410-0")).thenReturn(libro);

        boolean resultado = sistemaPrestamos.devolverLibro("978-3-16-148410-0");

        assertFalse(resultado);
        verify(catalogo).buscarLibro("978-3-16-148410-0");
    }

    @Test
    void testDevolverLibroInexistente() {
        when(catalogo.buscarLibro("000")).thenReturn(null);

        boolean resultado = sistemaPrestamos.devolverLibro("000");

        assertFalse(resultado);
        verify(catalogo).buscarLibro("000");
    }
}
