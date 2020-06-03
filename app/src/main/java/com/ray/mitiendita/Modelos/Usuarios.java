package com.ray.mitiendita.Modelos;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class Usuarios extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id_Usuario;
    @Column
    private String NombreUsuario;
    @Column
    private String Contraseña;

    public Usuarios() {
    }

    public Usuarios(String nombreUsuario, String contraseña) {
        NombreUsuario = nombreUsuario;
        Contraseña = contraseña;
    }

    public Usuarios(int id_Usuario, String nombreUsuario, String contraseña) {
        this.id_Usuario = id_Usuario;
        NombreUsuario = nombreUsuario;
        Contraseña = contraseña;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
}
