package com.ray.mitiendita.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ray.mitiendita.Modelos.Usuarios;
import com.ray.mitiendita.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        agregarUsuario();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, RegistroInicial.class);
            startActivity(intent);
            finish();
        },5000);

    }

    private void agregarUsuario() {

       String[] nombreUsuarios = {"admin"};
       String[] contraseñas = {"admin"};


       for (int i = 0; i < 1; i++){
           Usuarios usuarios = new Usuarios(nombreUsuarios[i],contraseñas[i]);
           try {
               usuarios.insert();
           } catch (Exception e) {

           }
       }

    }
}
