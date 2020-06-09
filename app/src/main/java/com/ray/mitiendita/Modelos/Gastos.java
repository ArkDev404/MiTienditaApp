package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class Gastos extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int idGasto;
    @Column
    private String motivoGasto;
    @Column
    private float monto;
    @Column
    private String fechaGasto;
    @Column
    private String comprobanteGasto;

    public Gastos() {
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public String getMotivoGasto() {
        return motivoGasto;
    }

    public void setMotivoGasto(String motivoGasto) {
        this.motivoGasto = motivoGasto;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(String fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    public String getComprobanteGasto() {
        return comprobanteGasto;
    }

    public void setComprobanteGasto(String comprobanteGasto) {
        this.comprobanteGasto = comprobanteGasto;
    }
}
