package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorVentas;
import com.ray.mitiendita.Listeners.OnItemVentaClickListener;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.AppPreferences_Table;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaVentas extends AppCompatActivity implements OnItemVentaClickListener {

    @BindView(R.id.recyclerVentas)
    RecyclerView recyclerVentas;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;

    private AdaptadorVentas adaptador;
    private AppPreferences appPreferences = new AppPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ventas);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<AppPreferences> appPreferencesList =
                SQLite.select().from(AppPreferences.class).queryList();

        if (appPreferencesList.get(0).getIsVenta()==0){
            crearShowCase();
        }

        configAdaptador();
        configRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
            adaptador.setList(getVentasDB());
            validarVistas();
    }

    private void crearShowCase() {

        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fab),
                "Crear Venta","Desde este boton puedes crear ventas de tus productos")
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

                        appPreferences.setIsVenta(1);

                        appPreferences.update();
                    }
                });

    }

    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerVentas.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerVentas.setVisibility(View.VISIBLE);
        }
    }

    private void configRecyclerView() {
        recyclerVentas.setLayoutManager(new LinearLayoutManager(this));
        recyclerVentas.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorVentas(this,new
                ArrayList<>());
    }

    private List<Ventas> getVentasDB() {
        return SQLite
                .select()
                .from(Ventas.class)
                .queryList();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, CrearVenta.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Ventas ventas) {
        Intent intent = new Intent(this,DetalleVenta.class);
        intent.putExtra(Ventas.ID, ventas.getIdFolio());
        startActivity(intent);
    }
}
