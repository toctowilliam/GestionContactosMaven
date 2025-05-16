package org.example.gestioncontactos.modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private File archivo;
    private List<Persona> personas;

    public PersonaDAO() {
      archivo = new File("datosContactos.csv"); 
      personas = new ArrayList<>();
      prepararArchivo();
  }

    public void prepararArchivo() {
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarPersona(Persona persona) {
        personas.add(persona);
    }

    public boolean escribirArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Persona p : personas) {
                bw.write(p.datosContacto());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Persona> leerArchivo() {
        personas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String nombre = datos[0];
                    String telefono = datos[1];
                    String email = datos[2];
                    String categoria = datos[3];
                    boolean favorito = Boolean.parseBoolean(datos[4]);
                    personas.add(new Persona(nombre, telefono, email, categoria, favorito));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public void actualizarContactos(List<Persona> personas) {
        this.personas = personas;
        escribirArchivo();
    }

}
