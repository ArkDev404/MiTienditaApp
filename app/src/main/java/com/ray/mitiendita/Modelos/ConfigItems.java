package com.ray.mitiendita.Modelos;

public class ConfigItems {

    private int id;
    private String Titulo;
    private String Subtitulo;
    private int Icono;

    public ConfigItems(int id, String titulo, String subtitulo, int icono) {
        this.id = id;
        Titulo = titulo;
        Subtitulo = subtitulo;
        Icono = icono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getSubtitulo() {
        return Subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        Subtitulo = subtitulo;
    }

    public int getIcono() {
        return Icono;
    }

    public void setIcono(int icono) {
        Icono = icono;
    }
}
