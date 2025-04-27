package gestores;

import enums.Estado;
import modelos.Libro;
import modelos.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class sistemaPrestamos {
    private Catalogo catalogo;
    private List<Prestamo> prestamos;

    public sistemaPrestamos(Catalogo catalogo) {
        this.catalogo = catalogo;
        this.prestamos = new ArrayList<>();
    }

    public boolean prestarLibro(String isbn) {
        Optional<Libro> optionalLibro = catalogo.buscarLibro(isbn);
        if (optionalLibro.isPresent()) {
            Libro libro = optionalLibro.get();
            if (libro.getEstado() == Estado.DISPONIBLE) {
                libro.SetEstado(Estado.PRESTADO);
                Prestamo prestamo = new Prestamo(libro);
                prestamos.add(prestamo);
                return true;
            }
        }
        return false;
    }

    public boolean devolverLibro(String isbn) {
        Optional<Libro> optionalLibro = catalogo.buscarLibro(isbn);
        if (optionalLibro.isPresent()) {
            Libro libro = optionalLibro.get();
            if (libro.getEstado() == Estado.PRESTADO) {
                libro.SetEstado(Estado.DISPONIBLE);
                Prestamo prestamo = new Prestamo(libro);
                prestamos.add(prestamo);
                return true;
            }
        }
        return false;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
