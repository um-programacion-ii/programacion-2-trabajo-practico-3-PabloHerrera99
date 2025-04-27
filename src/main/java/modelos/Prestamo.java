package modelos;

import java.time.LocalDate;

public class Prestamo {
    private Libro libro;
    private LocalDate fechaPrestamo;

    public Prestamo(Libro libro) {
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
    }

    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
}
