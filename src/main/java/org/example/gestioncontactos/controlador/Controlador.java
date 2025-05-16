package org.example.gestioncontactos.controlador;


import org.example.gestioncontactos.modelo.PersonaDAO;
import org.example.gestioncontactos.modelo.Persona;
import org.example.gestioncontactos.vista.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Controlador {

    private final Vista vista;
    private final PersonaDAO dao;
    private List<Persona> listaContactos;
    private Idioma idioma;
    private final Object bloqueoExportacion = new Object(); // Para sincronizar acceso al archivo
    private final Lock lockEdicion = new ReentrantLock();


    public Controlador(Vista vista, Idioma idioma) {
        this.vista = vista;
        this.idioma = idioma;
        this.dao = new PersonaDAO();


        vista.getBarraProgreso().setIndeterminate(true);
        new Thread(() -> {
            this.listaContactos = dao.leerArchivo();
            SwingUtilities.invokeLater(() -> {
                cargarContactosEnTabla(listaContactos);
                actualizarEstadisticas();
                vista.getBarraProgreso().setIndeterminate(false);
            });
        }).start();
        initListeners();
        cargarContactosEnTabla(listaContactos);
        configurarAtajosTeclado();
        configurarMenuContextual();

    }

    private void initListeners() {
        vista.getBtnAgregar().addActionListener(e -> agregarContacto());
        vista.getBtnModificar().addActionListener(e -> modificarContacto());
        vista.getBtnEliminar().addActionListener(e -> eliminarContacto());
        vista.getBtnExportar().addActionListener(e -> exportarCSV());

        vista.getChkFavorito().addActionListener(e -> filtrar());
        vista.getCmbCategoria().addActionListener(e -> filtrar());

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrar();
            }
        });

        vista.getTablaContactos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarCamposDesdeTabla();
            }
        });
    }

    private void agregarContacto() {
        String nombre = vista.getTxtNombres().getText().trim();
        String telefono = vista.getTxtTelefono().getText().trim();
        String email = vista.getTxtEmail().getText().trim();
        String categoria = vista.getCmbCategoria().getSelectedItem().toString();
        boolean favorito = vista.getChkFavorito().isSelected();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(vista, idioma.getTexto("mensaje.camposVacios"));
            return;
        }

        if (!email.matches(".+@.+\\..+")) {
            JOptionPane.showMessageDialog(vista, idioma.getTexto("mensaje.emailInvalido"));
            return;
        }

        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(vista, idioma.getTexto("mensaje.telefonoInvalido"));
            return;
        }

        Persona nuevoContacto = new Persona(nombre, telefono, email, categoria, favorito);
        validarYAgregarContacto(nuevoContacto); // solo esto, sin agregar ni guardar todavía
    }


    private void validarYAgregarContacto(Persona contacto) {
        //SwingWorker para realizar la validación en segundo plano
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                // Verificar si ya existe un contacto con el mismo teléfono
                for (Persona p : listaContactos) {
                    if (p.getTelefono().equals(contacto.getTelefono())) {
                        return false; // El contacto ya existe
                    }
                }
                mostrarNotificacion(idioma.getTexto("mensaje.contactoAgregado"));
                return true; // El contacto no existe
            }
            @Override
            protected void done() {
                try {
                    // Obtener el resultado de la validación
                    boolean esValido = get();
                    if (esValido) {
                        // Si es válido, agregar el contacto a la lista y actualizar la interfaz
                        listaContactos.add(contacto);
                        dao.actualizarContactos(listaContactos);
                        cargarContactosEnTabla(listaContactos);
                        limpiarCampos();
                        actualizarEstadisticas();
                    } else {
                        // Mostrar un mensaje de error en caso de que el contacto ya exista
                        JOptionPane.showMessageDialog(null, idioma.getTexto("mensaje.contactoExistente"), idioma.getTexto("titulo.error"), JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // Ejecutar el SwingWorker
        worker.execute();
    }

    // Objeto de bloqueo global para sincronización
    private final Object lockModificacion = new Object();

    private void modificarContacto() {
        int fila = vista.getTablaContactos().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un contacto para modificar.");
            return;
        }

        System.out.println("Fila seleccionada: " + fila);

        String nombre = vista.getTxtNombres().getText().trim();
        String telefono = vista.getTxtTelefono().getText().trim();
        String email = vista.getTxtEmail().getText().trim();
        String categoria = vista.getCmbCategoria().getSelectedItem().toString();
        boolean favorito = vista.getChkFavorito().isSelected();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben estar llenos.");
            return;
        }

        if (!email.matches(".+@.+\\..+")) {
            JOptionPane.showMessageDialog(vista, "El email no tiene un formato válido.");
            return;
        }

        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(vista, "El teléfono debe contener solo números.");
            return;
        }

        // Crear el objeto modificado
        Persona modificado = new Persona(nombre, telefono, email, categoria, favorito);

        // Obtener el teléfono del contacto seleccionado en la tabla (asumiendo que está en la primera columna)
        String telefonoSeleccionado = vista.getTablaContactos().getValueAt(fila, 1).toString();

        // Bloqueo sincronizado para evitar conflictos durante la modificación
        synchronized (lockModificacion) {
            // Buscar el índice real del contacto en la lista
            for (int i = 0; i < listaContactos.size(); i++) {
                if (listaContactos.get(i).getTelefono().equals(telefonoSeleccionado)) {
                    // Actualizar el contacto en la lista
                    listaContactos.set(i, modificado);
                    dao.actualizarContactos(listaContactos);
                    cargarContactosEnTabla(listaContactos);
                    mostrarNotificacion("Contacto modificado correctamente.");
                    limpiarCampos();
                    actualizarEstadisticas();
                    return;
                }
            }
            JOptionPane.showMessageDialog(vista, "No se encontró el contacto para modificar.");
        }
    }


    private void eliminarContacto() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un contacto para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista, idioma.getTexto("mensaje.confirmarEliminar"), idioma.getTexto("mensaje.confirmar"), JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Obtener el teléfono del contacto seleccionado (asumiendo que está en la primera columna)
            String telefonoSeleccionado = vista.getTablaContactos().getValueAt(fila, 1).toString();

            // Buscar el contacto en la lista basado en el teléfono
            for (int i = 0; i < listaContactos.size(); i++) {
                if (listaContactos.get(i).getTelefono().equals(telefonoSeleccionado)) {
                    listaContactos.remove(i);
                    dao.actualizarContactos(listaContactos);
                    cargarContactosEnTabla(listaContactos);
                    limpiarCampos();
                    break;  // Salir del bucle una vez eliminado
                }
            }
        }
        actualizarEstadisticas();
    }


    // Método para exportar la lista de contactos a un archivo CSV en segundo plano
    private void exportarCSV() {
        // Se abre un cuadro de diálogo para que el usuario elija dónde guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");

        int seleccion = fileChooser.showSaveDialog(vista);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Se asegura que el archivo tenga la extensión .csv
            if (!archivoSeleccionado.getName().toLowerCase().endsWith(".csv")) {
                archivoSeleccionado = new File(archivoSeleccionado.getAbsolutePath() + ".csv");
            }

            final File archivoFinal = archivoSeleccionado;
            // Se activa la barra de progreso en la interfaz
            vista.getBarraProgreso().setIndeterminate(true);

            // Se crea un hilo de trabajo en segundo plano para realizar la exportación
            SwingWorker<Void, Void> exportWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    // Bloqueo sincronizado para evitar exportaciones simultáneas al mismo archivo
                    synchronized (bloqueoExportacion) {
                        try (FileWriter writer = new FileWriter(archivoFinal)) {
                            // Se escribe cada contacto en una línea del archivo CSV
                            for (Persona p : listaContactos) {
                                writer.write(p.datosContacto() + "\n");
                                Thread.sleep(50); // Simula tiempo de procesamiento por contacto
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e); // Se lanza para manejar el error en done()
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // Se detiene la barra de progreso y se muestra un mensaje según el resultado
                    vista.getBarraProgreso().setIndeterminate(false);
                    try {
                        get(); // Verifica si ocurrió una excepción durante la exportación
                        JOptionPane.showMessageDialog(vista,
                                idioma.getTexto("mensaje.exportarExito") + ":\n" + archivoFinal.getAbsolutePath());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(vista, idioma.getTexto("mensaje.exportarError"));
                    }
                }
            };
            // Se ejecuta el hilo de exportación
            exportWorker.execute();
        }
    }

    private void cargarContactosEnTabla(List<Persona> lista) {
        String[] columnas = {
                idioma.getTexto("tabla.nombre"),
                idioma.getTexto("tabla.telefono"),
                idioma.getTexto("tabla.email"),
                idioma.getTexto("tabla.categoria"),
                idioma.getTexto("tabla.favorito")
        };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Persona p : lista) {
            Object[] fila = {
                p.getNombre(),
                p.getTelefono(),
                p.getEmail(),
                p.getCategoria(),
                p.isFavorito() ? "Sí" : "No"
            };
            modelo.addRow(fila);
        }

        vista.getTablaContactos().setModel(modelo);

        // Habilitar ordenamiento por columnas
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        vista.getTablaContactos().setRowSorter(sorter);
    }

    private void limpiarCampos() {
        vista.getTxtNombres().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtEmail().setText("");
        vista.getCmbCategoria().setSelectedIndex(0);
        vista.getChkFavorito().setSelected(false);
        vista.getTablaContactos().clearSelection();
    }

    private void cargarCamposDesdeTabla() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila != -1) {
            vista.getTxtNombres().setText((String) vista.getTablaContactos().getValueAt(fila, 0));
            vista.getTxtTelefono().setText((String) vista.getTablaContactos().getValueAt(fila, 1));
            vista.getTxtEmail().setText((String) vista.getTablaContactos().getValueAt(fila, 2));
            vista.getCmbCategoria().setSelectedItem(vista.getTablaContactos().getValueAt(fila, 3));
            vista.getChkFavorito().setSelected("Sí".equals(vista.getTablaContactos().getValueAt(fila, 4)));
        }
    }

    private void filtrar() {
        String texto = vista.getTxtBuscar().getText().toLowerCase();
        String categoria = vista.getCmbCategoria().getSelectedItem().toString();
        boolean soloFavoritos = vista.getChkFavorito().isSelected();

        buscarContactosEnSegundoPlano(texto, categoria, soloFavoritos);
    }

    private void buscarContactosEnSegundoPlano(String texto, String categoria, boolean soloFavoritos) {
        // Crear un SwingWorker para realizar la búsqueda en segundo plano
        SwingWorker<List<Persona>, Void> worker = new SwingWorker<List<Persona>, Void>() {
            @Override
            protected List<Persona> doInBackground() {
                // Filtrar la lista de contactos según los criterios proporcionados
                return listaContactos.stream()
                        .filter(p -> p.getNombre().toLowerCase().contains(texto)) // Filtrar por texto en el nombre
                        .filter(p -> categoria.equals(idioma.getTexto("categoria.todas")) || p.getCategoria().equals(categoria)) // Filtrar por categoría
                        .filter(p -> !soloFavoritos || p.isFavorito()) // Filtrar por favoritos si está activado
                        .collect(Collectors.toList());
            }

            @Override
            protected void done() {
                System.out.println("Filtrando contactos...");
                try {
                    // Obtener el resultado de la búsqueda y cargarlo en la tabla
                    List<Persona> resultado = get();
                    cargarContactosEnTabla(resultado);
                } catch (Exception e) {
                    // Manejar cualquier excepción que ocurra durante la ejecución
                    e.printStackTrace();
                }
            }
        };
        // Ejecutar el SwingWorker
        worker.execute();
    }

    public void actualizarEstadisticas() {
        int total = listaContactos.size();
        int favoritos = (int) listaContactos.stream().filter(Persona::isFavorito).count();
        int otros = (int) listaContactos.stream().filter(p -> p.getCategoria().equals(idioma.getTexto("categoria.otros"))).count();
        int amigos = (int) listaContactos.stream().filter(p -> p.getCategoria().equals(idioma.getTexto("categoria.amigos"))).count();
        int familia = (int) listaContactos.stream().filter(p -> p.getCategoria().equals(idioma.getTexto("categoria.familia"))).count();
        int trabajo = (int) listaContactos.stream().filter(p -> p.getCategoria().equals(idioma.getTexto("categoria.trabajo"))).count();

        vista.getLblTotalContactos().setText(idioma.getTexto("label.totalContactos") + ": " + total);
        vista.getLblFavoritos().setText(idioma.getTexto("label.otros") + ": " + otros);
        vista.getLblCategoriaAmigos().setText(idioma.getTexto("label.amigos") + ": " + amigos);
        vista.getLblCategoriaFamilia().setText(idioma.getTexto("label.familia") + ": " + familia);
        vista.getLblCategoriaTrabajo().setText(idioma.getTexto("label.trabajo") + ": " + trabajo);
    }

    private void configurarAtajosTeclado() {
        JRootPane rootPane = vista.getRootPane();
        // Ctrl + E = Exportar
        KeyStroke exportarKey = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(exportarKey, "exportar");
        rootPane.getActionMap().put("exportar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarCSV();
            }
        });

        // Ctrl + A = Agregar
        KeyStroke agregarKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(agregarKey, "agregar");
        rootPane.getActionMap().put("agregar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarContacto();
            }
        });
    }

    private void configurarMenuContextual() {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem editar = new JMenuItem("Editar");
        editar.addActionListener(e -> cargarCamposDesdeTabla());

        JMenuItem eliminar = new JMenuItem("Eliminar");
        eliminar.addActionListener(e -> eliminarContacto());

        menu.add(editar);
        menu.add(eliminar);

        vista.getTablaContactos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = vista.getTablaContactos().rowAtPoint(e.getPoint());
                    if (row != -1) {
                        vista.getTablaContactos().setRowSelectionInterval(row, row);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private void mostrarNotificacion(String mensaje) {
        new Thread(() -> {
            // Mostrar mensaje en el hilo de la UI
            SwingUtilities.invokeLater(() -> {
                vista.getLblNotificacion().setText(mensaje);
                vista.getLblNotificacion().setVisible(true);
            });

            try {
                // Mostrar la notificación por 3 segundos
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Ocultar notificación
            SwingUtilities.invokeLater(() -> {
                vista.getLblNotificacion().setVisible(false);
            });
        }).start();
    }

}
