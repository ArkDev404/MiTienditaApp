package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemClickListener;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {

    private List<Producto> productos;
    private Context context;
    private OnItemClickListener listener;

    public AdaptadorProducto(List<Producto> productos, OnItemClickListener listener) {
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        holder.setListener(producto,listener);
        holder.txtNombreProd.setText(producto.getNombreProducto());
        holder.txtCantidadProd.setText(String.valueOf(producto.getExistencias()));
        holder.txtPrecioProd.setText(String.valueOf(producto.getPrecio()));

        if (producto.getFotoProducto() != null) {

            RequestOptions options = new RequestOptions();

            Glide.with(context)
                    .load(producto.getFotoProducto())
                    .apply(options)
                    .into(holder.fotoProducto);
        } else {
            holder.fotoProducto.setImageDrawable(ContextCompat.
                    getDrawable(context,R.drawable.ic_shopping_basket_black_24dp));
        }

    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

    /**
     *
     * Metodo provisional
     */
    public void agregar(Producto producto) {
        if (!productos.contains(producto)){
            productos.add(producto);
            notifyDataSetChanged();
        }
    }

    public void remover(Producto producto){
        if (productos.contains(producto)){
            productos.remove(producto);
            notifyDataSetChanged();
        }
    }

    public void setList(List<Producto> productos) {
            this.productos = productos;
            // Notificamos los cambios realizados
            notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fotoProducto)
        CircleImageView fotoProducto;
        @BindView(R.id.txtNombreProd)
        MaterialTextView txtNombreProd;
        @BindView(R.id.txtPrecioProd)
        MaterialTextView txtPrecioProd;
        @BindView(R.id.txtCantidadProd)
        MaterialTextView txtCantidadProd;
        @BindView(R.id.containterProducto)
        ConstraintLayout containterProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void setListener(Producto producto, OnItemClickListener listener){
            containterProducto.setOnClickListener(v -> listener.onItemClick(producto));
            containterProducto.setOnLongClickListener(v -> {
                listener.ontItemLongClick(producto);
                return true;
            });
        }
    }
}
