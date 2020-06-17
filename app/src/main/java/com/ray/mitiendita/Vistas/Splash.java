package com.ray.mitiendita.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.Usuarios;
import com.ray.mitiendita.Modelos.Usuarios_Table;
import com.ray.mitiendita.R;

import java.util.List;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SQLite.select().from(AppPreferences.class).queryList().isEmpty()){
            crearPrefDefecto();
        }

        verificarDarkMode();

        setContentView(R.layout.splash);

        new Handler().postDelayed(() -> {

            List<Usuarios> listaUsuarios =
                    SQLite.
                            select()
                            .from(Usuarios.class)
                            .queryList();


            if (listaUsuarios.size()==0){
                Intent intent = new Intent(Splash.this, RegistroInicial.class);
                startActivity(intent);
                finish();
            } else {
                if (listaUsuarios.get(0).getActivo()==0){
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }

        },3500);

    }

    private void crearPrefDefecto() {
        AppPreferences appPreferences = new AppPreferences();

        appPreferences.setId(1);
        appPreferences.setIsDarkMode(0);
        appPreferences.setIsProducto(0);
        appPreferences.setIsCliente(0);
        appPreferences.setIsVenta(0);
        appPreferences.setIsGasto(0);

        appPreferences.insert();
    }

    private void verificarDarkMode() {
        List<AppPreferences> appPreferencesList
                = SQLite.select().from(AppPreferences.class).queryList();
        if (appPreferencesList.get(0).getIsDarkMode()==1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (appPreferencesList.get(0).getIsDarkMode()==0){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
