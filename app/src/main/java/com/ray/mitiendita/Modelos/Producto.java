package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDB.class)
public class Producto extends BaseModel {

    public static final String ID = "idProducto";
    //Elementos de la Tabla Producto
    @PrimaryKey(autoincrement = true)
    private int idProducto;
    @Column
    private String CodigoBarras;
    @Column
    private String NombreProducto;
    @Column
    private String Descripcion;
    @Column
    private float Precio;
    @Column
    private int Existencias;
    @Column
    private String FotoProducto;


    // Constructores
    public Producto() {
    }

    public Producto(String codigoBarras, String nombreProducto, String descripcion, float precio,
                    int existencias, String fotoProducto) {
        CodigoBarras = codigoBarras;
        NombreProducto = nombreProducto;
        Descripcion = descripcion;
        Precio = precio;
        Existencias = existencias;
        FotoProducto = fotoProducto;
    }

    public Producto(int idProducto, String codigoBarras, String nombreProducto, String descripcion,
                    float precio, int existencias, String fotoProducto) {
        this.idProducto = idProducto;
        CodigoBarras = codigoBarras;
        NombreProducto = nombreProducto;
        Descripcion = descripcion;
        Precio = precio;
        Existencias = existencias;
        FotoProducto = fotoProducto;
    }


    // Metodos Setter y Getter
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoBarras() {
        return CodigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        CodigoBarras = codigoBarras;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public int getExistencias() {
        return Existencias;
    }

    public void setExistencias(int existencias) {
        Existencias = existencias;
    }

    public String getFotoProducto() {
        return FotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        FotoProducto = fotoProducto;
    }
}
