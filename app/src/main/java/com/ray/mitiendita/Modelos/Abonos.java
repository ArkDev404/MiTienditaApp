package com.ray.mitiendita.Modelos;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class Abonos extends BaseModel {

    public static final String TOTAL = "totalAbonos";

    @PrimaryKey(autoincrement = true)
    private int idAbono;
    @Column
    private int idClienteDeudor;
    @Column
    private float montoAbono;

    public Abonos() {
    }

    public int getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public int getIdClienteDeudor() {
        return idClienteDeudor;
    }

    public void setIdClienteDeudor(int idClienteDeudor) {
        this.idClienteDeudor = idClienteDeudor;
    }

    public float getMontoAbono() {
        return montoAbono;
    }

    public void setMontoAbono(float montoAbono) {
        this.montoAbono = montoAbono;
    }
}
