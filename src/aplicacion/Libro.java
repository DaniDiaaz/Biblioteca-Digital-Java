package aplicacion;

public abstract class Libro {

	// Atributos privados
	private String isbn;
	private String titulo;
	private String autor;
	private int paginas;

	// Constructor
	public Libro(String isbn, String titulo, String autor, int paginas) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
	}
	
	// Getters y setters
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	
	// Métodos abstractos
	public abstract String mostrarTipo();
	public abstract String mostrarInfo();
}
