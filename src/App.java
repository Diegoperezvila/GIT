import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Libro> librosDisponibles = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Usuario usuarioActual = null;

    public App() {
        // Inicializar con algunos libros y usuarios para probar
        librosDisponibles.add(new Libro("El Hobbit", "J.R.R. Tolkien", "978-3-16-148410-0"));
        librosDisponibles.add(new Libro("1984", "George Orwell", "978-3-16-148411-7"));
        
        usuarios.add(new Usuario("Juan", "Pérez", "12345678", "juan@example.com", "aaaa1111"));
        usuarios.add(new Usuario("Ana", "Gómez", "87654321", "ana@example.com", "aaaa1111"));
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n1. Acceder a un usuario");
            System.out.println("2. Ver libros disponibles");
            System.out.println("3. Ver libros pendientes de devolver");
            System.out.println("4. Pedir un libro");
            System.out.println("5. Devolver un libro");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    accederAUsuario();
                    break;
                case 2:
                    verLibrosDisponibles();
                    break;
                case 3:
                    verLibrosPendientes();
                    break;
                case 4:
                    pedirLibro();
                    break;
                case 5:
                    devolverLibro();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    private boolean login() {
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.verificarContrasena(contrasena)) {
                usuarioActual = usuario;
                System.out.println("Login exitoso. Bienvenido, " + usuarioActual.getNombre());
                return true;
            }
        }
        System.out.println("Datos incorrectos. Por favor, intente nuevamente.");
        return false;
    }

    private void accederAUsuario() {
        System.out.print("Ingrese el email del usuario: ");
        String email = scanner.nextLine();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                usuarioActual = usuario;
                System.out.println("Acceso concedido a: " + usuario);
                return;
            }
        }
        System.out.println("Usuario no encontrado.");
    }
    

    private void verLibrosDisponibles() {
        if (librosDisponibles.isEmpty()) {
            System.out.println("No hay libros disponibles en este momento.");
            return;
        }
        System.out.println("Libros disponibles:");
        for (Libro libro : librosDisponibles) {
            System.out.println(libro);
        }
    }
    

    private void verLibrosPendientes() {
        if (usuarioActual == null) {
            System.out.println("Primero acceda a un usuario.");
            return;
        }
        List<Libro> pendientes = usuarioActual.getLibrosPendientesDeDevolver();
        if (pendientes.isEmpty()) {
            System.out.println("No tiene libros pendientes de devolver.");
            return;
        }
        System.out.println("Libros pendientes de devolver:");
        for (Libro libro : pendientes) {
            System.out.println(libro);
        }
    }


    private void pedirLibro() {
        if (usuarioActual == null) {
            System.out.println("Primero acceda a un usuario.");
            return;
        }
        System.out.print("Ingrese el ISBN del libro que desea pedir: ");
        String isbn = scanner.nextLine();

        // Buscar en la lista de libros disponibles.
        for (int i = 0; i < librosDisponibles.size(); i++) {
            Libro libro = librosDisponibles.get(i);
            if (libro.getIsbn().equals(isbn)) {
                usuarioActual.agregarLibroPendiente(libro);
                librosDisponibles.remove(libro);
                System.out.println("Libro agregado a sus pendientes: " + libro);
                return;
            }
        }

        // Si el libro no está en la lista de disponibles, comprobar si está en préstamo.
        for (Usuario usuario : usuarios) {
            for (Libro libro : usuario.getLibrosPendientesDeDevolver()) {
                if (libro.getIsbn().equals(isbn)) {
                    libro.añadirAListaDeEspera(usuarioActual);
                    System.out.println("Añadido a la lista de espera para el libro: " + libro);
                    return;
                }
            }
        }

        System.out.println("Libro no disponible o no encontrado.");
    }


    private void devolverLibro() {
        if (usuarioActual == null) {
            System.out.println("Primero acceda a un usuario.");
            return;
        }
        System.out.print("Ingrese el ISBN del libro que desea devolver: ");
        String isbn = scanner.nextLine();
        List<Libro> pendientes = usuarioActual.getLibrosPendientesDeDevolver();
        for (Libro libro : pendientes) {
            if (libro.getIsbn().equals(isbn)) {
                usuarioActual.removerLibroPendiente(libro);
                if (libro.tieneListaDeEspera()) {
                    Usuario siguienteUsuario = libro.siguienteEnCola();
                    siguienteUsuario.agregarLibroPendiente(libro);
                    System.out.println("El libro ha sido asignado al siguiente usuario en la lista de espera.");
                } else {
                    librosDisponibles.add(libro);
                    System.out.println("Libro devuelto exitosamente: " + libro);
                }
                return;
            }
        }
        System.out.println("No se encontró el libro en sus pendientes.");
    }


    private void registrarUsuario() {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese su teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();
        System.out.print("Cree una contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, apellido, telefono, email, contrasena);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado exitosamente.");
    }



    public static void main(String[] args) throws Exception {
        new App().iniciar();
    }
}
