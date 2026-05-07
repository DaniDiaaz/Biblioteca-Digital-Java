package aplicacion;

public class Revista extends Libro {
	
	// Atributos propios
	private String periodicidad;
	
	// Constructor propio y del padre
	public Revista(String isbn, String titulo, String autor, int paginas, String periodicidad) {
		super(isbn, titulo, autor, paginas);
		this.periodicidad = periodicidad;
	}

	// Getters y setters
	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}
	
	// Métodos sobreescritos
	@Override
	public String mostrarTipo() {
		return "Revista";
	}

	@Override
	public String mostrarInfo() {
		return "ISBN: " + getIsbn() + " | Título: " + getTitulo() + " | Autor: " + getAutor() + " | Páginas: " + getPaginas() + " | Periodicidad: " + periodicidad;
	}
	
}
