package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class Cliente extends BaseModel {

    public static final String ID = "idCliente";

    @PrimaryKey(autoincrement = true)
    private int idCliente;
    @Column
    private String Nombre;
    @Column
    private String Apellidos;
    @Column
    private String Sexo;
    @Column
    private float Saldo;

    public Cliente() {
    }

    public Cliente(String nombre, String apellidos, String sexo, float saldo) {
        Nombre = nombre;
        Apellidos = apellidos;
        Sexo = sexo;
        Saldo = saldo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float saldo) {
        Saldo = saldo;
    }
}
