package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistroInicial extends AppCompatActivity {

    @BindView(R.id.btnRegSiguiente)
    MaterialButton btnRegSiguiente;
    @BindView(R.id.txtInicioSesion)
    TextView txtInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inicial);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegSiguiente)
    public void onViewClicked() {
        Intent intent = new Intent(RegistroInicial.this, RegistroUsuario.class);
        startActivity(intent);
    }

    @OnClick(R.id.txtInicioSesion)
    public void onViewClickedSesion() {
        Intent intent = new Intent(RegistroInicial.this,Login.class);
        startActivity(intent);
    }
}
