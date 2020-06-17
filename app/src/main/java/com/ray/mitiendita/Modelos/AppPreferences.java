package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)

public class AppPreferences extends BaseModel {
    @PrimaryKey
    private int id;
    @Column
    private int isDarkMode;
    @Column
    private int isProducto;
    @Column
    private int isCliente;
    @Column
    private int isVenta;
    @Column
    private int isGasto;

    public AppPreferences() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDarkMode() {
        return isDarkMode;
    }

    public void setIsDarkMode(int isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public int getIsProducto() {
        return isProducto;
    }

    public void setIsProducto(int isProducto) {
        this.isProducto = isProducto;
    }

    public int getIsCliente() {
        return isCliente;
    }

    public void setIsCliente(int isCliente) {
        this.isCliente = isCliente;
    }

    public int getIsVenta() {
        return isVenta;
    }

    public void setIsVenta(int isVenta) {
        this.isVenta = isVenta;
    }

    public int getIsGasto() {
        return isGasto;
    }

    public void setIsGasto(int isGasto) {
        this.isGasto = isGasto;
    }
}


