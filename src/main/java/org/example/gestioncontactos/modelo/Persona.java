package org.example.gestioncontactos.modelo;

public class Persona {
    private String nombre;
    private String telefono;
    private String email;
    private String categoria;
    private boolean favorito;

    // Constructor
    public Persona(String nombre, String telefono, String email, String categoria, boolean favorito) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    // Métodos getter y setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    // Método para mostrar los datos de la persona en formato de cadena
    public String datosContacto() {
        return nombre + "," + telefono + "," + email + "," + categoria + "," + favorito;
    }

    // Método para mostrar la persona en un formato específico (por ejemplo, una lista)
    public String formatoLista() {
        return nombre + " - " + telefono + " - " + categoria + " - " + (favorito ? "Favorito" : "No Favorito");
    }
}
