package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class Ventas extends BaseModel {

    public static final String TOTAL = "totalDeuda";
    public static final String ID = "idFolio";
    @PrimaryKey
    private int idFolio;
    @Column
    private String FechaVenta;
    @Column
    private String EstaPagada;
    @Column //FK Cliente
    private int ClienteID;
    @Column
    private float totalVenta;


    public Ventas() {
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public String getFechaVenta() {
        return FechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        FechaVenta = fechaVenta;
    }

    public String getEstaPagada() {
        return EstaPagada;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getClienteID() {
        return ClienteID;
    }

    public void setClienteID(int clienteID) {
        ClienteID = clienteID;
    }

    public void setEstaPagada(String estaPagada) {
        EstaPagada = estaPagada;
    }
}
