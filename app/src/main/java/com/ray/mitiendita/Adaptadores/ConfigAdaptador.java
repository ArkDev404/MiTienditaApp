package com.ray.mitiendita.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.mitiendita.Modelos.ConfigItems;
import com.ray.mitiendita.R;

import java.util.List;

public class ConfigAdaptador extends BaseAdapter {

    Context context;
    List<ConfigItems> configItems;

    public ConfigAdaptador(Context context, List<ConfigItems> configItems) {
        this.context = context;
        this.configItems = configItems;
    }

    @Override
    public int getCount() {
        return configItems.size();
    }

    @Override
    public Object getItem(int position) {
        return configItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return configItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflate = LayoutInflater.from(context);
        vista = inflate.inflate(R.layout.items_configuracion,null);

        ImageView imagen =vista.findViewById(R.id.icono_configuracion);
        TextView titulo = vista.findViewById(R.id.txtConfig);
        TextView descripcion = vista.findViewById(R.id.txtConfigDescrip);

        titulo.setText(configItems.get(position).getTitulo());
        descripcion.setText(configItems.get(position).getSubtitulo());
        imagen.setImageResource(configItems.get(position).getIcono());

        return vista;
    }
}
