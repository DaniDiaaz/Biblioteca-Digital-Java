package aplicacion;

public class LibroFisico extends Libro implements Prestable {

	// Atributos propios
	private String ubicacion;
	private boolean prestado;

	// Constructor propio y del padre
	public LibroFisico(String isbn, String titulo, String autor, int paginas, String ubicacion, boolean prestado) {
		super(isbn, titulo, autor, paginas);
		this.ubicacion = ubicacion;
		this.prestado = prestado;
	}

	// Getters y setters
	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	// Métodos sobreescritos
	@Override
	public boolean prestar() {
		if (!prestado) {
			prestado = true; // Préstamo exitoso
			return true;
		} else {
			return false; // Ya estaba prestado
		}
	}

	@Override
	public void devolver() {
		prestado = false;
	}

	@Override
	public boolean isPrestado() {
		return prestado;
	}

	@Override
	public String mostrarTipo() {
		return "Libro físico";
	}

	@Override
	public String mostrarInfo() {
		return "ISBN: " + getIsbn() + " | Título: " + getTitulo() + " | Autor: " + getAutor() + " | Páginas: " + getPaginas() + " | Ubicación: " + ubicacion;
	}
}
