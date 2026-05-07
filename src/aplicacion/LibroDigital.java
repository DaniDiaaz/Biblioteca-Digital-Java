package aplicacion;

public class LibroDigital extends Libro implements Prestable {

	// Atributos propios
	private String formato;
	private boolean prestado;

	// Constructor propio y del padre
	public LibroDigital(String isbn, String titulo, String autor, int paginas, String formato, boolean prestado) {
		super(isbn, titulo, autor, paginas);
		this.formato = formato;
		this.prestado = prestado;
	}

	// Getters y setters
	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
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
		return "Libro digital";
	}

	@Override
	public String mostrarInfo() {
		return "ISBN: " + getIsbn() + " | Título: " + getTitulo() + " | Autor: " + getAutor() + " | Páginas: "
				+ getPaginas() + " | Formato: " + formato;
	}

}
