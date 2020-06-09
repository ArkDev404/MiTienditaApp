package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorGastos;
import com.ray.mitiendita.Listeners.OnItemGastoClickListener;
import com.ray.mitiendita.Modelos.Gastos;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaGastos extends AppCompatActivity implements OnItemGastoClickListener {

    @BindView(R.id.recyclerGastos)
    RecyclerView recyclerGastos;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;

    private AdaptadorGastos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        ButterKnife.bind(this);

        configAdaptador();
        configRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getGastos());
        validarVistas();
    }

    private List<Gastos> getGastos() {
        return SQLite
                .select()
                .from(Gastos.class)
                .queryList();
    }

    private void configRecyclerView() {
        recyclerGastos.setLayoutManager(new LinearLayoutManager(this));
        recyclerGastos.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorGastos(new ArrayList<>(), this);
    }

    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerGastos.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerGastos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Gastos gastos) {

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, AgregarGasto.class);
        startActivity(intent);
    }
}
