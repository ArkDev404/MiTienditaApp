package com.ray.mitiendita.Modelos;

public class Categorias {

    private int fotoCategoria;
    private String NombreCategoria;

    public Categorias() {
    }

    public Categorias(int fotoCategoria, String nombreCategoria) {
        this.fotoCategoria = fotoCategoria;
        NombreCategoria = nombreCategoria;
    }

    public int getFotoCategoria() {
        return fotoCategoria;
    }

    public void setFotoCategoria(int fotoCategoria) {
        this.fotoCategoria = fotoCategoria;
    }

    public String getNombreCategoria() {
        return NombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        NombreCategoria = nombreCategoria;
    }
}
