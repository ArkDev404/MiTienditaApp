package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Modelos.Categorias;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {

    private List<Categorias> categorias;
    private Context context;

    public AdaptadorCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Categorias categoria = categorias.get(position);
        holder.fotoCategoria.setImageResource(categoria.getFotoCategoria());
        holder.txtCategoria.setText(categoria.getNombreCategoria());
    }

    @Override
    public int getItemCount() {
        return this.categorias.size();
    }

    public void agregar(Categorias categoria) {
        if (!categorias.contains(categoria)){
            categorias.add(categoria);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fotoCategoria)
        CircleImageView fotoCategoria;
        @BindView(R.id.txtCategoria)
        MaterialTextView txtCategoria;
        @BindView(R.id.containerCategoria)
        ConstraintLayout containerCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
