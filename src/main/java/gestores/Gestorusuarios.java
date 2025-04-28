package gestores;

import modelos.Libro;
import modelos.Prestamo;
import modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Gestorusuarios {
    private List<Usuario> usuarios;
    private Catalogo catalogo;
    private SistemaPrestamos sistemaPrestamos;

    public Gestorusuarios(Catalogo catalogo, SistemaPrestamos sistemaPrestamos) {
        this.usuarios = new ArrayList<>();
        this.catalogo = catalogo;
        this.sistemaPrestamos = sistemaPrestamos;
    }

    public Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public void registrarUsuario(String nombre) {
        if (buscarUsuario(nombre) == null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        usuarios.add(new Usuario(nombre));
    }

    public void registrarPrestamo(String nombre, String isbn) {
        Usuario usuario = buscarUsuario(nombre);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        Libro libro = catalogo.buscarLibro(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("El libro no existe");
        }

        Prestamo prestamo = sistemaPrestamos.prestarLibro(isbn);
        if (prestamo == null){
            throw new IllegalArgumentException("El recurso no esta disponible");
        }
        usuario.agregarPrestamo(prestamo);
    }
}
