package org.example.gestioncontactos.controlador;

import java.util.Locale;
import java.util.ResourceBundle;

public class Idioma {
    private ResourceBundle bundle;

    public Idioma(String idioma, String pais) {
        Locale locale = new Locale(idioma, pais);
        bundle = ResourceBundle.getBundle("idiomas.Messages", locale);
    }

    public String getTexto(String clave) {
        return bundle.getString(clave);
    }
}
