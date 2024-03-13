import java.util.ArrayList;
import java.util.List;

public class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private List<Usuario> colaDeEspera;

    public Libro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.colaDeEspera = new ArrayList<>();
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void añadirAListaDeEspera(Usuario usuario) {
        colaDeEspera.add(usuario);
    }

    public boolean tieneListaDeEspera() {
        return !colaDeEspera.isEmpty();
    }

    public Usuario siguienteEnCola() {
        if (!colaDeEspera.isEmpty()) {
            return colaDeEspera.remove(0); // Retorna y elimina el primer usuario en la lista
        }
        return null;
    }

    @Override
    public String toString() {
        return titulo + " por " + autor + " (ISBN: " + isbn + ")";
    }
}
