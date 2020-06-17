package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.ConfigAdaptador;
import com.ray.mitiendita.Listeners.OnItemConfigClickListener;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.AppPreferences_Table;
import com.ray.mitiendita.Modelos.ConfigItems;
import com.ray.mitiendita.Modelos.Usuarios;
import com.ray.mitiendita.Modelos.Usuarios_Table;
import com.ray.mitiendita.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Dashboard extends AppCompatActivity implements OnItemConfigClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cardProductos)
    CardView cardProductos;
    @BindView(R.id.layoutPanel)
    LinearLayout layoutPanel;
    @BindView(R.id.listConfig)
    RecyclerView listConfig;
    @BindView(R.id.Navigator)
    BottomNavigationView Navigator;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.gridLayout)
    GridLayout gridLayout;
    @BindView(R.id.switch_nightMode)
    SwitchMaterial switchNightMode;

    private ConfigAdaptador adaptador;
    private Usuarios usuarios = new Usuarios();
    private AppPreferences appPreferences = new AppPreferences();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        configAdaptador();
        configRecyclerView();

        switchNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            appPreferences = SQLite.select().from(AppPreferences.class)
                    .where(AppPreferences_Table.id.is(1)).querySingle();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                appPreferences.setIsDarkMode(1);
                appPreferences.update();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                appPreferences.setIsDarkMode(0);
                appPreferences.update();
            }
        });

        Navigator.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.mnu_home:

                    listConfig.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    gridLayout.setVisibility(View.VISIBLE);

                    toolbar.setSubtitle("Inicio");

                    return true;

                case R.id.mnu_configuracion: {

                    listConfig.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    gridLayout.setVisibility(View.GONE);

                    toolbar.setSubtitle("Configuración");

                    return true;
                }
            }
            return true;
        });


        agregarConfiguraciones();

    }


    private void configRecyclerView() {
        listConfig.setLayoutManager(new LinearLayoutManager(this));
        listConfig.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new ConfigAdaptador(new ArrayList<>(),this);
    }

    private void agregarConfiguraciones() {

        int[] logos = {R.drawable.ic_settings,R.drawable.ic_info_blue_24dp, R.drawable.logout};
        String[] titulos = {"Cuenta", "Info de la Aplicación","Cerrar Sesión"};
        String[] descripcion = {"Cambio de Contraseña","Versión, Datos de Desarrollo","Salir de la App"};

        for (int i = 0; i < 3 ; i++) {
            ConfigItems configItems = new ConfigItems(i,titulos[i],descripcion[i],logos[i]);
            adaptador.agregar(configItems);
        }
    }


    @OnClick(R.id.cardProductos)
    public void onStartProductos() {
        Intent intent = new Intent(getApplicationContext(), Productos.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardClientes)
    public void onStartClientes() {
        Intent intent = new Intent(getApplicationContext(), ListaClientes.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardVentas)
    public void onStartVentas() {
        Intent intent = new Intent(this, ListaVentas.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardEstadisticas)
    public void onStartEstadisticas() {
        Intent intent = new Intent(this, Estadisticas.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardGastos)
    public void onStartGastos() {
        Intent intent = new Intent(this, ListaGastos.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardCorte)
    public void onViewClicked() {
        Intent intent = new Intent(this, CorteAlDia.class);
        startActivity(intent);
    }

    @Override
    public void OnItemClick(ConfigItems configItems) {

        if (configItems.getId() == 0){
            Intent intent = new Intent(this, CambiarDatosCuenta.class);
            startActivity(intent);
        }

        if (configItems.getId() == 1) {
            Intent intent = new Intent(this,InfoAplicacion.class);
            startActivity(intent);
        }
        //Provisional - Cerrar Sesion
        if (configItems.getId() == 2) {
            usuarios = SQLite
                    .select()
                    .from(Usuarios.class)
                    .where(Usuarios_Table.id_Usuario.is(1))
                    .querySingle();

            usuarios.setActivo(0);

            usuarios.update();

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (listConfig.getVisibility()==View.VISIBLE){

        } else if (gridLayout.getVisibility()==View.VISIBLE){
            finish();
        }
    }
}
