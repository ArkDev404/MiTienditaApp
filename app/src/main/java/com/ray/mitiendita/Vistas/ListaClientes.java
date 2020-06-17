package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorCliente;
import com.ray.mitiendita.Listeners.OnItemClienteClickListener;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.AppPreferences_Table;
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
    private AppPreferences appPreferences = new AppPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        List<AppPreferences> appPreferencesList =
                SQLite.select().from(AppPreferences.class).queryList();

        if (appPreferencesList.get(0).getIsCliente()==0){
            crearShowCase();
        }
        configAdaptador();
        configRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getClientesDB());
        validarVistas();
    }

    private void crearShowCase() {

        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fab),
                "Alta de Cliente","Al dar clic en este boton puedes dar de alta " +
                        "nuevos clientes")
                        .outerCircleColor(R.color.color_Primary)
                        .outerCircleAlpha(0.8f)
                        .targetCircleColor(R.color.md_white_1000)
                        .titleTextSize(25)
                        .titleTextColor(R.color.md_white_1000)
                        .descriptionTextSize(15)
                        .descriptionTextColor(R.color.md_white_1000)
                        .dimColor(R.color.md_black_1000)
                        .drawShadow(true)
                        .cancelable(false)
                        .tintTarget(false)
                        .transparentTarget(true)
                        .targetRadius(30),
                new TapTargetView.Listener(){
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        appPreferences = SQLite.select().from(AppPreferences.class)
                                .where(AppPreferences_Table.id.is(1)).querySingle();

                        appPreferences.setIsCliente(1);

                        appPreferences.update();
                    }
                });

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
