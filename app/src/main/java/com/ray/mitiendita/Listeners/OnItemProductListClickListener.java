package com.ray.mitiendita.Listeners;

import com.ray.mitiendita.Modelos.DetalleVentas;

public interface OnItemProductListClickListener {
    void onItemClick(DetalleVentas detalleVentas);
    void onItemLongClick(DetalleVentas detalleVentas);
}
