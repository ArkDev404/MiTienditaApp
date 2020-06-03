package com.ray.mitiendita.Listeners;

import com.ray.mitiendita.Modelos.Producto;

public interface OnItemClickListener {
    void onItemClick(Producto producto);
    void ontItemLongClick(Producto producto);
}
