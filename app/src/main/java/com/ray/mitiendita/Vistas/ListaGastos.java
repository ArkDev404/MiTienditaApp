package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorGastos;
import com.ray.mitiendita.Listeners.OnItemGastoClickListener;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.AppPreferences_Table;
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
    private AppPreferences appPreferences = new AppPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        ButterKnife.bind(this);

        List<AppPreferences> appPreferencesList =
                SQLite.select().from(AppPreferences.class).queryList();

        if (appPreferencesList.get(0).getIsGasto()==0){
            crearShowCase();
        }

        configAdaptador();
        configRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getGastos());
        validarVistas();
    }


    private void crearShowCase() {

        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fab),
                "Registro de Gastos","Desde este boton puedes almacenar tus gastos del dia a dia" +
                        " (Pago proveedores, servicios, etc.)")
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

                        appPreferences.setIsGasto(1);

                        appPreferences.update();
                    }
                });

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
        Intent intent = new Intent(this, DetalleGasto.class);
        intent.putExtra(Gastos.ID,gastos.getIdGasto());
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, AgregarGasto.class);
        startActivity(intent);
    }
}
