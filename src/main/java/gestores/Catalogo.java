package gestores;

import enums.Estado;
import modelos.Libro;

import java.util.*;

public class Catalogo {
    private List<Libro> libros;

    public Catalogo() {
        this.libros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarLibro(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public List<Libro> getLibrosDisponibles() {
        List<Libro> librosDisponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getEstado() == Estado.DISPONIBLE) {
                librosDisponibles.add(libro);
            }
        }
        return librosDisponibles;
    }

}
