package org.example.gestioncontactos;

import org.example.gestioncontactos.controlador.Controlador;
import org.example.gestioncontactos.controlador.Idioma;
import org.example.gestioncontactos.vista.Vista;

public class Main {
    
    public static void main(String[] args) {
        // Crear la vista
        Vista vista = new Vista();
        // Crear el idioma
        Idioma idioma = new Idioma("es", "ES");

        Controlador controlador = new Controlador(vista, idioma);

        // Mostrar la interfaz
        vista.setVisible(true);
        vista.setLocationRelativeTo(null); // Centra la ventana 
    }
}
