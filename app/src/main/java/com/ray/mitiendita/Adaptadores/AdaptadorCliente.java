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

import com.google.android.material.textview.MaterialTextView;
import com.ray.mitiendita.Listeners.OnItemClienteClickListener;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.ViewHolder> {

    private List<Cliente> clientes;
    private OnItemClienteClickListener listener;
    private Context context;

    public AdaptadorCliente(List<Cliente> clientes, OnItemClienteClickListener listener) {
        this.clientes = clientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cliente cliente = clientes.get(position);
        holder.setListener(cliente, listener);
        holder.txtNombreCliente.setText(cliente.getNombre());
        holder.txtSaldo.setText(String.valueOf(cliente.getSaldo()));

        if (cliente.getSexo().equals("Hombre")) {
            holder.fotoCliente.setImageDrawable(ContextCompat.
                    getDrawable(context,R.drawable.ic_hombre));
        } else {
            holder.fotoCliente.setImageDrawable(ContextCompat.
                    getDrawable(context,R.drawable.ic_mujer));
        }
    }

    @Override
    public int getItemCount() {
        return this.clientes.size();
    }

    /*Metodo provisional*/
    public void remover(Cliente cliente) {
        if (clientes.contains(cliente)) {
            clientes.remove(cliente);
            notifyDataSetChanged();
        }
    }

    public void setList(List<Cliente> clientes) {
        this.clientes = clientes;
        // Notificamos los cambios realizados
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNombreCliente)
        MaterialTextView txtNombreCliente;
        @BindView(R.id.txtSaldo)
        MaterialTextView txtSaldo;
        @BindView(R.id.containterCliente)
        ConstraintLayout containterCliente;
        @BindView(R.id.fotoCliente)
        CircleImageView fotoCliente;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setListener(Cliente cliente, OnItemClienteClickListener listener) {
            containterCliente.setOnClickListener(v -> listener.onItemClick(cliente));
            containterCliente.setOnLongClickListener(v -> {
                listener.onItemLongClick(cliente);
                return true;
            });
        }
    }
}
