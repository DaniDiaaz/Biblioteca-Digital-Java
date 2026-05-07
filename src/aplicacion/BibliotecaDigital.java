package aplicacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// BibliotecaDigital extiende JFrame, es decir, ES una ventana
public class BibliotecaDigital extends JFrame {

	// Objeto Biblioteca que contiene el ArrayList de libros
	private Biblioteca biblioteca;

	// Componentes visuales declarados como atributos para poder acceder
	// a ellos desde cualquier método de la clase
	private JTable tabla; // Tabla donde se muestran los libros
	private JScrollPane scrollPane; // Contenedor con scroll para la tabla
	private JComboBox<String> comboOrden; // ComboBox para elegir criterio de ordenación
	private JButton btnAnadir; // Abre el JDialog para añadir un libro
	private JButton btnPrestar; // Presta el libro seleccionado en la tabla
	private JButton btnGuardar; // Guarda la biblioteca en un archivo TXT
	private JButton btnCargar; // Carga la biblioteca desde un archivo TXT

	public BibliotecaDigital() {

		// Inicializamos la biblioteca (el ArrayList vacío)
		biblioteca = new Biblioteca();

		// 1) CONFIGURACIÓN DE LA VENTANA
		// Siempre antes de añadir componentes
		setTitle("Biblioteca Digital");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la app al cerrar la ventana
		setLocationRelativeTo(null); // Centra la ventana en la pantalla
		setLayout(new BorderLayout()); // Divide la ventana en NORTH, CENTER, SOUTH

		// 2) INICIALIZACIÓN DE COMPONENTES

		// ComboBox con los criterios de ordenación
		String[] criterios = { "Título", "Autor", "Páginas" };
		comboOrden = new JComboBox<>(criterios);

		// Botones de la parte inferior
		btnAnadir = new JButton("Añadir Libro");
		btnPrestar = new JButton("Prestar");
		btnGuardar = new JButton("Guardar TXT");
		btnCargar = new JButton("Cargar TXT");

		// 3) LISTENERS DE LOS BOTONES
		// Cada botón tiene un ActionListener que se ejecuta al hacer clic

		// GUARDAR TXT
		// Abre el explorador de archivos para que el usuario elija dónde guardar
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int resultado = fileChooser.showSaveDialog(null); // Abre ventana "Guardar como"
				if (resultado == JFileChooser.APPROVE_OPTION) { // Si el usuario no canceló
					String archivo = fileChooser.getSelectedFile().getAbsolutePath(); // Ruta completa del archivo
					biblioteca.guardarTXT(archivo);
					JOptionPane.showMessageDialog(null, "Biblioteca guardada correctamente");
					actualizarTabla();
				}
			}
		});

		// CARGAR TXT
		// Abre el explorador de archivos para que el usuario elija qué archivo cargar
		btnCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int resultado = fileChooser.showOpenDialog(null); // Abre ventana "Abrir"
				if (resultado == JFileChooser.APPROVE_OPTION) { // Si el usuario no canceló
					String archivo = fileChooser.getSelectedFile().getAbsolutePath();
					biblioteca.cargarTXT(archivo);
					JOptionPane.showMessageDialog(null, "Biblioteca cargada correctamente");
					actualizarTabla(); // Refrescamos la tabla con los datos cargados
				}
			}
		});

		// PRESTAR
		// Obtiene el libro seleccionado en la tabla y lo presta si es Prestable
		btnPrestar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tabla.getSelectedRow(); // Índice de la fila seleccionada (-1 si ninguna)

				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un libro primero");
					return; // Salimos del método si no hay fila seleccionada
				}

				// El índice de la fila coincide con el índice del ArrayList
				Libro libro = biblioteca.getLibros().get(filaSeleccionada);

				if (libro instanceof Prestable) {
					Prestable p = (Prestable) libro; // Casting para poder usar prestar()
					if (p.prestar()) {
						JOptionPane.showMessageDialog(null, "Libro prestado correctamente");
					} else {
						JOptionPane.showMessageDialog(null, "El libro ya está prestado");
					}
				} else {
					// Las revistas no implementan Prestable
					JOptionPane.showMessageDialog(null, "Las revistas no se pueden prestar");
				}
				actualizarTabla(); // Refrescamos para actualizar el estado en la tabla
			}
		});

		// AÑADIR LIBRO
		// Abre un JDialog modal con un formulario para introducir los datos del libro
		btnAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JDialog modal: bloquea la ventana principal hasta que se cierre
				JDialog dialog = new JDialog(BibliotecaDigital.this, "Añadir Libro", true);
				dialog.setSize(400, 300);
				dialog.setLocationRelativeTo(null);
				// GridLayout(0, 2): 2 columnas, filas automáticas, 5px de separación
				dialog.setLayout(new GridLayout(0, 2, 5, 5));

				// Campos del formulario (etiqueta + campo de texto)
				JLabel lblIsbn = new JLabel("ISBN:");
				JTextField txtIsbn = new JTextField();

				JLabel lblTitulo = new JLabel("Título:");
				JTextField txtTitulo = new JTextField();

				JLabel lblAutor = new JLabel("Autor:");
				JTextField txtAutor = new JTextField();

				JLabel lblPaginas = new JLabel("Páginas:");
				JTextField txtPaginas = new JTextField();

				JLabel lblTipo = new JLabel("Tipo:");
				String[] tipos = { "Libro Físico", "Libro Digital", "Revista" };
				JComboBox<String> comboTipo = new JComboBox<>(tipos);

				JButton btnConfirmar = new JButton("Añadir");
				JButton btnCancelar = new JButton("Cancelar");

				// Añadimos los componentes al dialog en orden
				// GridLayout los coloca de izquierda a derecha, de arriba a abajo
				dialog.add(lblIsbn);
				dialog.add(txtIsbn);
				dialog.add(lblTitulo);
				dialog.add(txtTitulo);
				dialog.add(lblAutor);
				dialog.add(txtAutor);
				dialog.add(lblPaginas);
				dialog.add(txtPaginas);
				dialog.add(lblTipo);
				dialog.add(comboTipo);
				dialog.add(btnConfirmar);
				dialog.add(btnCancelar);

				// CONFIRMAR: recoge los datos y crea el objeto correspondiente
				btnConfirmar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String isbn = txtIsbn.getText();
						String titulo = txtTitulo.getText();
						String autor = txtAutor.getText();
						int paginas = Integer.parseInt(txtPaginas.getText()); // Convertimos String a int
						String tipo = (String) comboTipo.getSelectedItem();

						// Creamos el objeto según el tipo seleccionado (polimorfismo)
						if (tipo.equals("Libro Físico")) {
							biblioteca.agregarLibro(
									new LibroFisico(isbn, titulo, autor, paginas, "Sin ubicación", false));
						} else if (tipo.equals("Libro Digital")) {
							biblioteca
									.agregarLibro(new LibroDigital(isbn, titulo, autor, paginas, "Sin formato", false));
						} else if (tipo.equals("Revista")) {
							biblioteca.agregarLibro(new Revista(isbn, titulo, autor, paginas, "Sin periodicidad"));
						}
						actualizarTabla(); // Refrescamos la tabla con el nuevo libro
						dialog.dispose(); // Cerramos y liberamos memoria del dialog
					}
				});

				// CANCELAR: simplemente cierra el dialog sin hacer nada
				btnCancelar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});

				dialog.setVisible(true); // Siempre al final, después de añadir todo
			}
		});

		// COMBOBOX DE ORDENACIÓN
		// Al cambiar la selección, ordena el ArrayList y refresca la tabla
		comboOrden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String criterio = (String) comboOrden.getSelectedItem();
				biblioteca.ordenar(criterio); // Ordena el ArrayList con Comparator
				actualizarTabla(); // Refresca la tabla con el nuevo orden
			}
		});

		// 4) TABLA Y PANEL DE BOTONES
		tabla = new JTable();
		scrollPane = new JScrollPane(tabla); // Envolvemos la tabla en un ScrollPane

		JPanel panelBotones = new JPanel(); // Panel intermedio para agrupar los botones en SOUTH
		panelBotones.add(btnAnadir);
		panelBotones.add(btnPrestar);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCargar);

		// 5) AÑADIR COMPONENTES A LA VENTANA
		add(comboOrden, BorderLayout.NORTH); // ComboBox arriba
		add(scrollPane, BorderLayout.CENTER); // Tabla en el centro
		add(panelBotones, BorderLayout.SOUTH); // Botones abajo

		// 6) LIBROS DE EJEMPLO
		// Demuestran polimorfismo: todos son Libro pero de distintos tipos
		biblioteca.agregarLibro(new LibroFisico("001", "El Quijote", "Cervantes", 500, "Estantería A", false));
		biblioteca.agregarLibro(new LibroDigital("002", "Dune", "Frank Herbert", 412, "PDF", false));
		biblioteca.agregarLibro(new Revista("003", "National Geographic", "VV.AA.", 120, "Mensual"));
		biblioteca.agregarLibro(new LibroFisico("004", "1984", "George Orwell", 328, "Estantería B", true));
		actualizarTabla(); // Cargamos los libros en la tabla al arrancar

		// 7) MOSTRAR VENTANA
		// Siempre el último paso
		setVisible(true);
	}

	// Refresca la JTable con el estado actual del ArrayList
	// Se llama después de cualquier operación que modifique la biblioteca
	private void actualizarTabla() {
		String[] columnas = { "Tipo", "Título", "Autor", "Páginas", "Estado" };
		// DefaultTableModel gestiona los datos de la JTable
		// El 0 indica que empieza sin filas, las añadimos en el bucle
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		for (Libro libro : biblioteca.getLibros()) {
			String estado;

			// instanceof Prestable distingue libros prestables de revistas
			if (libro instanceof Prestable) {
				Prestable p = (Prestable) libro; // Casting para usar isPrestado()
				estado = p.isPrestado() ? "🔴 Prestado" : "🟢 Disponible";
			} else {
				estado = "N/A"; // Las revistas no tienen estado de préstamo
			}

			// Cada fila es un Object[] con los datos en el mismo orden que las columnas
			Object[] fila = { libro.mostrarTipo(), // Polimorfismo: cada subclase devuelve su tipo
					libro.getTitulo(), libro.getAutor(), libro.getPaginas(), estado };
			modelo.addRow(fila);
		}

		tabla.setModel(modelo); // Asignamos el modelo a la tabla para que se muestre
	}

	public static void main(String[] args) {
		new BibliotecaDigital(); // Lanzamos la aplicación
	}
}