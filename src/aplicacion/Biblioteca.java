package aplicacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Biblioteca {

	// Atributos propios
	private ArrayList<Libro> libros;

	// Constructor
	public Biblioteca() {
		libros = new ArrayList<Libro>();
	}

	// Métodos
	public ArrayList<Libro> getLibros() { // Devuelve la lista entera de libros que tiene la biblioteca
		return libros;
	}

	public void agregarLibro(Libro libro) { // Parámetro de tipo Libro que nos permite pasarle cualqier tipo de Libro
		libros.add(libro);
	}

	public void ordenar(String criterio) {
		switch (criterio) {
		case "Título":
			Collections.sort(libros, new Comparator<Libro>() {

				@Override
				public int compare(Libro a, Libro b) {
					return a.getTitulo().compareTo(b.getTitulo());
				}
			});
			break;
		case "Autor":
			Collections.sort(libros, new Comparator<Libro>() {

				@Override
				public int compare(Libro a, Libro b) {
					return a.getAutor().compareTo(b.getAutor());
				}
			});
			break;
		case "Páginas":
			Collections.sort(libros, new Comparator<Libro>() {

				@Override
				public int compare(Libro a, Libro b) {
					return Integer.compare(a.getPaginas(), b.getPaginas());
				}
			});
			break;
		}
	}

	public void guardarTXT(String archivo) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

			for (Libro libro : libros) {
				String linea = "";

				if (libro instanceof LibroFisico) {
					LibroFisico lf = (LibroFisico) libro; // eso se llama casting, le decimos a Java "trata este Libro
															// como un LibroFisico" para poder usar getUbicacion() que
															// no existe en la clase padre.
					linea = "LibroFisico;" + lf.getIsbn() + ";" + lf.getTitulo() + ";" + lf.getAutor() + ";"
							+ lf.getPaginas() + ";" + lf.getUbicacion() + ";" + lf.isPrestado();
				} else if (libro instanceof LibroDigital) {
					LibroDigital ld = (LibroDigital) libro;
					linea = "LibroDigital;" + ld.getIsbn() + ";" + ld.getTitulo() + ";" + ld.getAutor() + ";"
							+ ld.getPaginas() + ";" + ld.getFormato() + ";" + ld.isPrestado();
				} else if (libro instanceof Revista) {
					Revista rv = (Revista) libro;
					linea = "Revista;" + rv.getIsbn() + ";" + rv.getTitulo() + ";" + rv.getAutor() + ";"
							+ rv.getPaginas() + ";" + rv.getPeriodicidad() + ";N/A";
				}
				writer.write(linea);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarTXT(String archivo) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(archivo));
			
			libros.clear(); // Vaciamos la lista antes de cargar
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(";");
				
				String tipo = partes[0];
				String isbn = partes[1];
				String titulo = partes[2];
				String autor = partes[3];
				int paginas = Integer.parseInt(partes[4]);
				String datosExtra = partes[5];
				String prestado = partes.length > 6 ? partes[6] : "N/A";
				
				if (tipo.equals("LibroFisico")) {
					boolean esPrestado = Boolean.parseBoolean(prestado);
					LibroFisico lf = new LibroFisico(isbn, titulo, autor, paginas, datosExtra, esPrestado);
					libros.add(lf);
				} else if (tipo.equals("LibroDigital")) {
					boolean esPrestado = Boolean.parseBoolean(prestado);
					LibroDigital ld = new LibroDigital(isbn, titulo, autor, paginas, datosExtra, esPrestado);
					libros.add(ld);
				} else if (tipo.equals("Revista")) {
					Revista rv = new Revista(isbn, titulo, autor, paginas, datosExtra);
					libros.add(rv);
				}
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
