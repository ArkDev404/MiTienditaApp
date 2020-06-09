package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemGastoClickListener;
import com.ray.mitiendita.Modelos.Gastos;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorGastos extends RecyclerView.Adapter<AdaptadorGastos.ViewHolder> {

    private List<Gastos> gastos;
    private Context context;
    private OnItemGastoClickListener listener;

    public AdaptadorGastos(List<Gastos> gastos, OnItemGastoClickListener listener) {
        this.gastos = gastos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gasto, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Gastos gasto = gastos.get(position);
        holder.setListener(gasto,listener);
        holder.txtMotivo.setText(gasto.getMotivoGasto());
        holder.txtMonto.setText(String.valueOf(gasto.getMonto()));
        holder.txtFechaGasto.setText(gasto.getFechaGasto());
    }

    @Override
    public int getItemCount() {
        return this.gastos.size();
    }

    public void setList(List<Gastos> gastos ) {
        this.gastos = gastos;
        // Notificamos los cambios realizados
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fotoComprobante)
        CircleImageView fotoComprobante;
        @BindView(R.id.txtMotivo)
        MaterialTextView txtMotivo;
        @BindView(R.id.txtMonto)
        MaterialTextView txtMonto;
        @BindView(R.id.txtFechaGasto)
        MaterialTextView txtFechaGasto;
        @BindView(R.id.containerGasto)
        ConstraintLayout containerGasto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void setListener(Gastos gasto, OnItemGastoClickListener listener){
            containerGasto.setOnClickListener(v -> listener.onItemClick(gasto));
        }
    }


}
