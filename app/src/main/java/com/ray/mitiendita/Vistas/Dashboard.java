package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ray.mitiendita.Adaptadores.ConfigAdaptador;
import com.ray.mitiendita.Modelos.ConfigItems;
import com.ray.mitiendita.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Dashboard extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cardProductos)
    CardView cardProductos;
    @BindView(R.id.layoutPanel)
    LinearLayout layoutPanel;
    @BindView(R.id.listConfig)
    ListView listConfig;
    @BindView(R.id.Navigator)
    BottomNavigationView Navigator;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.gridLayout)
    GridLayout gridLayout;

    ArrayList<ConfigItems> configItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


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

        listConfig.setOnItemClickListener((parent, view, position, id) -> {
            //Provisional
            if (position == 3) {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        });

       /* new MaterialShowcaseView.Builder(this)
                .setTarget(cardProductos)
                .setDismissText("Cerrar")
                .setContentText("Selecciona una de estas opciones para comenzar a trabajar")
                .setContentTextColor(getColor(R.color.md_white_1000))
                .withCircleShape()
                .show(); */
    }

    private void agregarConfiguraciones() {

        configItems = new ArrayList<ConfigItems>();

        configItems.add(new ConfigItems(1, "Negocio", "Ajustes de tu negocio",
                R.drawable.negocio));
        configItems.add(new ConfigItems(2, "Cuenta", "Datos de tu cuenta, contraseñas",
                R.drawable.ic_settings));
        configItems.add(new ConfigItems(3, "Info de la aplicación", "Datos del Desarrollador",
                R.drawable.ic_info_blue_24dp));
        configItems.add(new ConfigItems(3, "Cerrar Sesión", "Cierra sesión para mayor seguridad",
                R.drawable.logout));

        ConfigAdaptador configAdaptador = new ConfigAdaptador(getApplicationContext(), configItems);

        listConfig.setAdapter(configAdaptador);
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
    public void onViewClicked() {
        Intent intent = new Intent(this, ListaVentas.class);
        startActivity(intent);
    }
}
