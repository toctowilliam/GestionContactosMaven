package org.example.gestioncontactos.vista;

import org.example.gestioncontactos.controlador.Idioma;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * @author willi
 */
public class Vista extends JFrame {

    private Idioma idioma;

    public Vista() {
        // idioma = new Idioma("en", "US");
        idioma = new Idioma("es", "ES");
        //idioma = new Idioma("fr", "FR");
        initComponents();
        personalizarBotones();
        personalizarTabla();
        personalizarCampos();
        configurarTextos();
    }

    private void personalizarBotones() {
        personalizarBoton(btn_agregar);
        personalizarBoton(btn_eliminar);
        personalizarBoton(btn_modifcar);
        personalizarBoton(btn_exportar);
    }

    private void personalizarBoton(JButton boton) {
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(new Color(0, 153, 204)); // Azul bonito
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));

        // Borde redondeado y definido
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true), // Borde azul fuerte
                BorderFactory.createEmptyBorder(8, 20, 8, 20) // Espaciado interno
        ));
    }

    private void personalizarComboBox(JComboBox comboBox) {
        comboBox.setBackground(new Color(255, 255, 255)); // Fondo blanco
        comboBox.setForeground(new Color(0, 51, 102));    // Texto azul oscuro
        comboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true)); // Borde azul redondeado
    }

    private void personalizarTextField(JTextField textField) {
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(0, 51, 102));  // Azul oscuro
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true), // Borde azul redondeado
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espaciado interno
        ));
    }

    private void personalizarCampos() {
        // ComboBox
        personalizarComboBox(cmb_categoria);

        // TextFields
        personalizarTextField(txt_buscar);
        personalizarTextField(txt_email);
        personalizarTextField(txt_nombres);
        personalizarTextField(txt_telefono);
    }

    private void personalizarTabla() {
        tabla_contactos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(0, 120, 215)); // Azul para selección
                    c.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE); // Filas pares
                    } else {
                        c.setBackground(new Color(224, 242, 255)); // Filas impares (azul clarito)
                    }
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        tabla_contactos.setRowHeight(25); // Altura de filas un poco más alta
    }


    private void configurarTextos() {
        setTitle(idioma.getTexto("titulo.ventana"));
        jLabel1.setText(idioma.getTexto("label.estadisticas"));
        lbl_totalContactos.setText(idioma.getTexto("label.totalContactos"));
        lbl_favoritos.setText(idioma.getTexto("label.favoritos"));
        lbl_categoriaFamilia.setText(idioma.getTexto("label.familia"));
        lbl_categoriaAmigos.setText(idioma.getTexto("label.amigos"));
        lbl_categoriaTrabajo.setText(idioma.getTexto("label.trabajo"));
        jTabbedPane2.setTitleAt(0, idioma.getTexto("tab.estadisticas"));
        jTabbedPane2.setTitleAt(1, idioma.getTexto("tab.gestioncContactos"));
        chk_favorito.setText(idioma.getTexto("checkbox.favorito"));
        lbl_nombre.setText(idioma.getTexto("label.nombre"));
        lbl_telefono.setText(idioma.getTexto("label.telefono"));
        lbl_email.setText(idioma.getTexto("label.email"));
        btn_agregar.setText(idioma.getTexto("button.agregar"));
        btn_modifcar.setText(idioma.getTexto("button.modificar"));
        btn_eliminar.setText(idioma.getTexto("button.eliminar"));
        btn_exportar.setText(idioma.getTexto("button.exportar"));
        lbl_buscar.setText(idioma.getTexto("label.buscarNombre"));
        cmb_categoria.setModel(new DefaultComboBoxModel<>(new String[]{
                idioma.getTexto("categoria.amigos"),
                idioma.getTexto("categoria.familia"),
                idioma.getTexto("categoria.trabajo"),
                idioma.getTexto("categoria.otros")
        }));

    }

    private void initComponents() {

        jTabbedPane2 = new JTabbedPane();
        jPanel2 = new JPanel();
        jPanel6 = new JPanel();
        jLabel1 = new JLabel();
        jPanel5 = new JPanel();
        lbl_totalContactos = new JLabel();
        lbl_favoritos = new JLabel();
        lbl_categoriaFamilia = new JLabel();
        lbl_categoriaAmigos = new JLabel();
        lbl_categoriaTrabajo = new JLabel();
        jPanel1 = new JPanel();
        chk_favorito = new JCheckBox();
        cmb_categoria = new JComboBox<>();
        barra_progreso = new JProgressBar();
        jPanel3 = new JPanel();
        lbl_nombre = new JLabel();
        lbl_telefono = new JLabel();
        lbl_email = new JLabel();
        txt_nombres = new JTextField();
        txt_telefono = new JTextField();
        txt_email = new JTextField();
        btn_agregar = new JButton();
        btn_modifcar = new JButton();
        btn_eliminar = new JButton();
        jPanel4 = new JPanel();
        jScrollPane1 = new JScrollPane();
        tabla_contactos = new JTable();
        btn_exportar = new JButton();
        txt_buscar = new JTextField();
        lbl_buscar = new JLabel();
        lblNotificacion = new JLabel();
        lblNotificacion.setText("");
        lblNotificacion.setVisible(false); // No se muestra al iniciar
        lblNotificacion.setFont(new Font("Arial", Font.BOLD, 12)); // Cambia el tamaño y estilo de la fuente
        lblNotificacion.setForeground(Color.RED); // Cambia el color del texto a rojo
        lblNotificacion.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
        lblNotificacion.setPreferredSize(new java.awt.Dimension(150, 10));
        lblNotificacion.setMinimumSize(new java.awt.Dimension(150, 10));
        lblNotificacion.setMaximumSize(new java.awt.Dimension(150, 10));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE CONTACTOS");

        jTabbedPane2.setBackground(new Color(102, 102, 102));

        jPanel2.setBackground(new Color(204, 204, 204));

        jPanel6.setBackground(new Color(204, 204, 204));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));

        jLabel1.setBackground(new Color(102, 102, 102));
        jLabel1.setFont(new Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setForeground(new Color(0, 102, 102));
        jLabel1.setText("Estadísticas del total de Contactos por Categoria.");

        jPanel5.setBackground(new Color(204, 204, 204));
        jPanel5.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)));

        lbl_totalContactos.setBackground(new Color(0, 102, 102));
        lbl_totalContactos.setForeground(new Color(0, 102, 102));
        lbl_totalContactos.setText("Total Contactos");

        lbl_favoritos.setForeground(new Color(0, 102, 102));
        lbl_favoritos.setText("Favoritos");

        lbl_categoriaFamilia.setForeground(new Color(0, 102, 102));
        lbl_categoriaFamilia.setText("Familia");

        lbl_categoriaAmigos.setForeground(new Color(0, 102, 102));
        lbl_categoriaAmigos.setText("Amigos");

        lbl_categoriaTrabajo.setForeground(new Color(0, 102, 102));
        lbl_categoriaTrabajo.setText("Trabajo");

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_categoriaTrabajo, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_favoritos, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_totalContactos, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_categoriaFamilia, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_categoriaAmigos, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(lbl_totalContactos, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_favoritos, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_categoriaFamilia, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_categoriaAmigos, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_categoriaTrabajo, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 105, Short.MAX_VALUE))
                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(54, Short.MAX_VALUE))
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Estadisticas", jPanel2);

        jPanel1.setBackground(new Color(204, 204, 204));

        chk_favorito.setBackground(new Color(204, 204, 204));
        chk_favorito.setForeground(new Color(0, 102, 102));
        chk_favorito.setText("Contacto Favorito");
        chk_favorito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_favoritoActionPerformed(evt);
            }
        });

        cmb_categoria.setModel(new DefaultComboBoxModel<>(new String[]{"Amigos", "Familia", "Trabajo", "Otros"}));
        cmb_categoria.setToolTipText("");
        cmb_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_categoriaActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new Color(204, 204, 204));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));

        lbl_nombre.setForeground(new Color(0, 102, 102));
        lbl_nombre.setText("Nombres:");
        lbl_nombre.setToolTipText("");

        lbl_telefono.setForeground(new Color(0, 102, 102));
        lbl_telefono.setText("Telefono:");

        lbl_email.setForeground(new Color(0, 102, 102));
        lbl_email.setText("Correo:");

        txt_nombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombresActionPerformed(evt);
            }
        });

        txt_telefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonoActionPerformed(evt);
            }
        });

        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        btn_agregar.setBackground(new Color(0, 102, 102));
        btn_agregar.setForeground(Color.white);
        btn_agregar.setIcon(new ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        btn_agregar.setText("Agregar");
        btn_agregar.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_modifcar.setBackground(new Color(0, 102, 102));
        btn_modifcar.setForeground(Color.white);
        btn_modifcar.setIcon(new ImageIcon(getClass().getResource("/imagenes/modify.png"))); // NOI18N
        btn_modifcar.setText("Modificar");
        btn_modifcar.setBorder(BorderFactory.createEtchedBorder());
        btn_modifcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifcarActionPerformed(evt);
            }
        });

        btn_eliminar.setBackground(new Color(0, 102, 102));
        btn_eliminar.setForeground(Color.white);
        btn_eliminar.setIcon(new ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setBorder(BorderFactory.createEtchedBorder());
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_nombre)
                                        .addComponent(lbl_telefono)
                                        .addComponent(lbl_email)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(50) // Espaciado adicional para mover lblNotificacion
                                                .addComponent(lblNotificacion)))
                                .addGap(15, 15,15)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_nombres, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_telefono, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_email, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNotificacion, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addComponent(btn_agregar, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btn_eliminar, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_modifcar, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_nombre)
                                        .addComponent(txt_nombres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_telefono)
                                        .addComponent(txt_telefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_email)
                                        .addComponent(txt_email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNotificacion))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_agregar)
                                        .addComponent(btn_eliminar)
                                        .addComponent(btn_modifcar))
                                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new Color(204, 204, 204));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new Color(153, 153, 153), 1, true));

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new Color(0, 51, 51), 1, true));
        jScrollPane1.setViewportBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true));
        jScrollPane1.setFont(new Font("Arial Narrow", 0, 12)); // NOI18N
        jScrollPane1.setInheritsPopupMenu(true);

        tabla_contactos.setBackground(new Color(204, 204, 255));
        tabla_contactos.setForeground(new Color(153, 0, 51));
        tabla_contactos.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        idioma.getTexto("tabla.nombre"),
                        idioma.getTexto("tabla.telefono"),
                        idioma.getTexto("tabla.email"),
                        idioma.getTexto("tabla.categoria")
                }
        ));
        jScrollPane1.setViewportView(tabla_contactos);

        btn_exportar.setIcon(new ImageIcon(getClass().getResource("/imagenes/exportar.png"))); // NOI18N
        btn_exportar.setText("Exportar CSV");

        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });

        lbl_buscar.setForeground(new Color(0, 102, 102));
        lbl_buscar.setText("Buscar por Nombre:");

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lbl_buscar)
                                .addGap(18, 18, 18)
                                .addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, 501, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_exportar)
                                .addGap(28, 28, 28))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(27, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 1035, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_exportar)
                                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lbl_buscar)
                                                .addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(18, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(chk_favorito)
                                .addGap(43, 43, 43)
                                .addComponent(cmb_categoria, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel4, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(barra_progreso, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 1049, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(chk_favorito)
                                        .addComponent(cmb_categoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(barra_progreso, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Gestion Contactos", jPanel1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jTabbedPane2)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane2))
        );

        pack();
    }// </editor-fold>                        

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void cmb_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_categoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_categoriaActionPerformed

    private void chk_favoritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_favoritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_favoritoActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void txt_telefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonoActionPerformed

    private void txt_nombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombresActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void btn_modifcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifcarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modifcarActionPerformed


    public JButton getBtnAgregar() {
        return btn_agregar;
    }

    public JTextField getTxtNombres() {
        return txt_nombres;
    }

    public JTextField getTxtTelefono() {
        return txt_telefono;
    }

    public JTextField getTxtEmail() {
        return txt_email;
    }

    public JComboBox<String> getCmbCategoria() {
        return cmb_categoria;
    }

    public JCheckBox getChkFavorito() {
        return chk_favorito;
    }

    public JTable getTablaContactos() {
        return tabla_contactos;
    }

    public JProgressBar getBarraProgreso() {
        return barra_progreso;
    }

    public JButton getBtnExportar() {
        return btn_exportar;
    }


    public JTextField getTxtBuscar() {
        return txt_buscar;
    }

    public JButton getBtnModificar() {
        return btn_modifcar;
    }

    public JButton getBtnEliminar() {
        return btn_eliminar;
    }

    public JLabel getLblTotalContactos() {
        return lbl_totalContactos;
    }

    public JLabel getLblFavoritos() {
        return lbl_favoritos;
    }

    public JLabel getLblCategoriaTrabajo() {
        return lbl_categoriaTrabajo;
    }

    public JLabel getLblCategoriaFamilia() {
        return lbl_categoriaFamilia;
    }

    public JLabel getLblCategoriaAmigos() {
        return lbl_categoriaAmigos;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tabla_contactos.getModel();
    }

    public JLabel getLblNotificacion() {
        return lblNotificacion;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JProgressBar barra_progreso;
    private JButton btn_agregar;
    private JButton btn_eliminar;
    private JButton btn_exportar;
    private JButton btn_modifcar;
    private JCheckBox chk_favorito;
    private JComboBox<String> cmb_categoria;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JTabbedPane jTabbedPane2;
    private JLabel lbl_buscar;
    private JLabel lbl_categoriaAmigos;
    private JLabel lbl_categoriaFamilia;
    private JLabel lbl_categoriaTrabajo;
    private JLabel lbl_email;
    private JLabel lbl_favoritos;
    private JLabel lbl_nombre;
    private JLabel lbl_telefono;
    private JLabel lbl_totalContactos;
    private JTable tabla_contactos;
    private JTextField txt_buscar;
    private JTextField txt_email;
    private JTextField txt_nombres;
    private JTextField txt_telefono;
    private JLabel lblNotificacion;


    // End of variables declaration//GEN-END:variables
}
