package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorVentas;
import com.ray.mitiendita.Listeners.OnItemVentaClickListener;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.Modelos.Cliente_Table;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.Modelos.Ventas_Table;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleSaldoCliente extends AppCompatActivity implements OnItemVentaClickListener {

    int idClienteDeudor;
    private AdaptadorVentas adaptador;

    @BindView(R.id.recyclerVentasCliente)
    RecyclerView recyclerVentasCliente;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_saldo_cliente);
        ButterKnife.bind(this);
        configAdaptador();
        configRecyclerView();
        recibirIDCliente(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getVentasCliente());
        validarVistas();
    }

    private void configRecyclerView() {
        recyclerVentasCliente.setLayoutManager(new LinearLayoutManager(this));
        recyclerVentasCliente.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorVentas(this, new ArrayList<>());
    }

    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerVentasCliente.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerVentasCliente.setVisibility(View.VISIBLE);
        }
    }

    private void recibirIDCliente(Intent intent) {
        idClienteDeudor = intent.getIntExtra(Cliente.ID, 0);
    }


    /**
     * Esta consulta equivale en SQL a:
     * Select * From Cliente inner join Ventas
     * on Clientes.idCliente = Ventas.idCliente
     * Where idCliente = x
     * Donde x es el id del cliente a consultar
     *
     * @return
     */
    private List<Ventas> getVentasCliente() {
        return SQLite
                .select()
                .from(Ventas.class)
                .innerJoin(Cliente.class)
                .on(Cliente_Table.idCliente.withTable().eq(Ventas_Table.ClienteID.withTable()))
                .where(Ventas_Table.ClienteID.is(idClienteDeudor))
                .queryList();
    }

    @Override
    public void onItemClick(Ventas ventas) {

    }
}
