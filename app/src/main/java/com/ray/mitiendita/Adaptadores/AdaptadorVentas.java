package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemVentaClickListener;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.R;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorVentas extends RecyclerView.Adapter<AdaptadorVentas.ViewHolder> {

    private Context context;
    private OnItemVentaClickListener listener;
    private List<Ventas> venta;

    DecimalFormat format = new DecimalFormat("#,###.00");

    public AdaptadorVentas(OnItemVentaClickListener listener, List<Ventas> venta) {
        this.listener = listener;
        this.venta = venta;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Ventas ventas = venta.get(position);
        holder.setListener(ventas, listener);
        holder.txtNoVenta.setText("Venta No. " + ventas.getIdFolio());
        holder.txtTotalVenta.setText(format.format(ventas.getTotalVenta()));
        holder.txtFechaGasto.setText(String.valueOf(ventas.getFechaVenta()));

    }

    @Override
    public int getItemCount() {
        return this.venta.size();
    }

    public void setList(List<Ventas> venta) {
        this.venta = venta;
        // Notificamos los cambios realizados
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNoVenta)
        MaterialTextView txtNoVenta;
        @BindView(R.id.txtTotalVenta)
        MaterialTextView txtTotalVenta;
        @BindView(R.id.txtFechaGasto)
        MaterialTextView txtFechaGasto;
        @BindView(R.id.containterVenta)
        ConstraintLayout containterVenta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setListener(Ventas ventas, OnItemVentaClickListener listener) {
            containterVenta.setOnClickListener(v -> listener.onItemClick(ventas));
        }
    }
}
