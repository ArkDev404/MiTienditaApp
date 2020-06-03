package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemProductListClickListener;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorListaP extends RecyclerView.Adapter<AdaptadorListaP.ViewHolder> {

    private Context context;
    private List<DetalleVentas> detalleVentas;
    private OnItemProductListClickListener listener;

    public AdaptadorListaP(List<DetalleVentas> detalleVentas, OnItemProductListClickListener listener) {
        this.detalleVentas = detalleVentas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_productos,
                parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DetalleVentas detalleVenta = detalleVentas.get(position);
        holder.setListener(detalleVenta,listener);
        holder.txtProducto.setText(detalleVenta.getProductoVendido());
        holder.txtCantidad.setText(String.valueOf(detalleVenta.getCantidad()));
        holder.txtTotalVenta.setText(String.valueOf(detalleVenta.getPrecio()*detalleVenta.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return this.detalleVentas.size();
    }

    public void agregar(DetalleVentas detalleVenta) {
        if (!detalleVentas.contains(detalleVenta)){
            detalleVentas.add(detalleVenta);
            notifyDataSetChanged();
        }
    }

    public void remover(DetalleVentas detalleVenta) {
        if (detalleVentas.contains(detalleVenta)){
            detalleVentas.remove(detalleVenta);
            notifyDataSetChanged();
        }
    }

    public void setList(List<DetalleVentas> detalleVentas) {
        this.detalleVentas = detalleVentas;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtProducto)
        MaterialTextView txtProducto;
        @BindView(R.id.txtCantidad)
        MaterialTextView txtCantidad;
        @BindView(R.id.txtTotalVenta)
        MaterialTextView txtTotalVenta;
        @BindView(R.id.containterVenta)
        LinearLayout containterVenta;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void setListener(DetalleVentas detalleVenta, OnItemProductListClickListener listener) {
            containterVenta.setOnClickListener(v -> listener.onItemClick(detalleVenta));
            containterVenta.setOnLongClickListener(v -> {
                listener.onItemLongClick(detalleVenta);
                return true;
            });
        }

    }

}
