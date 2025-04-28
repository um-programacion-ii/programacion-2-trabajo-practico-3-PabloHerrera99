package gestores;

import enums.Estado;
import modelos.Libro;
import modelos.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaPrestamos {
    private Catalogo catalogo;
    private List<Prestamo> prestamos;

    public SistemaPrestamos(Catalogo catalogo) {
        this.catalogo = catalogo;
        this.prestamos = new ArrayList<>();
    }

    public Prestamo prestarLibro(String isbn) {
        Libro libro = catalogo.buscarLibro(isbn);
        if (libro != null && libro.getEstado() == Estado.DISPONIBLE) {
            libro.setEstado(Estado.PRESTADO);
            Prestamo prestamo = new Prestamo(libro);
            prestamos.add(prestamo);
            return prestamo;
        }
        return null;
    }

    public boolean devolverLibro(String isbn) {
        Libro libro = catalogo.buscarLibro(isbn);
        if (libro != null && libro.getEstado() == Estado.PRESTADO) {
            libro.setEstado(Estado.DISPONIBLE);
            Prestamo prestamo = new Prestamo(libro);
            prestamos.add(prestamo);
            return true;
        }
        return false;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
