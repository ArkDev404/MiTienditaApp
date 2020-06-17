package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorListaP;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.Modelos.Cliente_Table;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.Modelos.DetalleVentas_Table;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.Modelos.Ventas_Table;
import com.ray.mitiendita.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleVenta extends AppCompatActivity {

    @BindView(R.id.tv_folio)
    TextView tvFolio;
    @BindView(R.id.txtFecha)
    TextView txtFecha;
    @BindView(R.id.etCliente)
    TextInputEditText etCliente;
    @BindView(R.id.recyclerProductosVendidos)
    RecyclerView recyclerProductosVendidos;
    @BindView(R.id.tv_total)
    MaterialTextView tvTotal;

    private Ventas ventas;
    private Cliente cliente;

    private AdaptadorListaP adaptador;

    DecimalFormat format = new DecimalFormat("#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);
        ButterKnife.bind(this);

        recibirDatosVenta(getIntent());

        configAdaptador();
        configRecyclerView();

        adaptador.setList(getListaProductos());
    }

    private void configRecyclerView() {
        recyclerProductosVendidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProductosVendidos.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorListaP(new ArrayList<>(), null);
    }




    private List<DetalleVentas> getListaProductos() {
        return SQLite
                .select()
                .from(DetalleVentas.class)
                .where(DetalleVentas_Table.folioVenta.is(Integer.valueOf(tvFolio.getText().toString())))
                .queryList();
    }

    private void recibirDatosVenta(Intent intent) {
        getVenta(intent.getIntExtra(Ventas.ID, 0));

        // Datos cliente
        cliente = SQLite
                .select(Cliente_Table.Nombre)
                .from(Cliente.class)
                .where(Cliente_Table.idCliente.is(ventas.getClienteID()))
                .querySingle();

        tvFolio.setText(String.valueOf(ventas.getIdFolio()));
        txtFecha.setText(ventas.getFechaVenta());
        etCliente.setText(cliente.getNombre());
        tvTotal.setText(format.format(ventas.getTotalVenta()));
    }



    private void getVenta(int id) {
        ventas = SQLite
                .select()
                .from(Ventas.class)
                .where(Ventas_Table.idFolio.is(id))
                .querySingle();
    }

}
