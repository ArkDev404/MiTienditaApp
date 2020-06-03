package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorCliente;
import com.ray.mitiendita.Listeners.OnItemClienteClickListener;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaClientes extends AppCompatActivity implements OnItemClienteClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerClientes)
    RecyclerView recyclerClientes;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;

    private AdaptadorCliente adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        configAdaptador();
        configRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getClientesDB());
        validarVistas();
    }

    private List<Cliente> getClientesDB() {
        return SQLite
                .select()
                .from(Cliente.class)
                .queryList();
    }

    private void configRecyclerView() {
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerClientes.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorCliente(new
                ArrayList<>(), this);
    }


    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerClientes.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerClientes.setVisibility(View.VISIBLE);
        }
    }


    /**
     *
     * @param cliente
     * Enviamos el ID del cliente para poder lanzar cargar sus datos en el detalle
     */
    @Override
    public void onItemClick(Cliente cliente) {
        Intent intent = new Intent(this, DetalleCliente.class);
        intent.putExtra(Cliente.ID,cliente.getIdCliente());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(Cliente cliente) {

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, AgregarCliente.class);
        startActivity(intent);
    }
}
