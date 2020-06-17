package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemConfigClickListener;
import com.ray.mitiendita.Modelos.ConfigItems;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigAdaptador extends RecyclerView.Adapter<ConfigAdaptador.ViewHolder> {

    private List<ConfigItems> configItems;
    private OnItemConfigClickListener listener;
    private Context context;

    public ConfigAdaptador(List<ConfigItems> configItems, OnItemConfigClickListener listener) {
        this.configItems = configItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_configuracion, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ConfigItems configItems1 = configItems.get(position);
        holder.setListener(configItems1,listener);
        holder.iconoConfiguracion.setImageResource(configItems1.getIcono());
        holder.txtConfig.setText(configItems1.getTitulo());
        holder.txtConfigDescrip.setText(configItems1.getSubtitulo());

    }

    @Override
    public int getItemCount() {
        return this.configItems.size();
    }


    public void agregar(ConfigItems configItems1) {
        if (!configItems.contains(configItems1)){
            configItems.add(configItems1);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icono_configuracion)
        ImageView iconoConfiguracion;
        @BindView(R.id.txtConfig)
        MaterialTextView txtConfig;
        @BindView(R.id.txtConfigDescrip)
        MaterialTextView txtConfigDescrip;
        @BindView(R.id.containerConfig)
        ConstraintLayout containerConfig;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setListener(ConfigItems configItems, OnItemConfigClickListener listener) {
            containerConfig.setOnClickListener(v -> listener.OnItemClick(configItems));
        }
    }
}
