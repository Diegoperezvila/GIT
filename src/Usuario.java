import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String contrasena;
    private List<Libro> librosPendientesDeDevolver;

    public Usuario(String nombre, String apellido, String telefono, String email, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena; // Inicializar la contraseña
        this.librosPendientesDeDevolver = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean verificarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public void agregarLibroPendiente(Libro libro) {
        librosPendientesDeDevolver.add(libro);
    }

    public boolean removerLibroPendiente(Libro libro) {
        return librosPendientesDeDevolver.remove(libro);
    }

    public List<Libro> getLibrosPendientesDeDevolver() {
        return librosPendientesDeDevolver;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + email + ")";
    }
}
