package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ray.mitiendita.Modelos.Negocio;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistroInicial extends AppCompatActivity {

    @BindView(R.id.btnRegSiguiente)
    MaterialButton btnRegSiguiente;
    @BindView(R.id.txtInicioSesion)
    TextView txtInicioSesion;
    @BindView(R.id.txtNombreNegocio)
    TextInputEditText txtNombreNegocio;
    @BindView(R.id.til_negocio)
    TextInputLayout tilNegocio;

    Negocio negocio = new Negocio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inicial);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegSiguiente)
    public void onViewClicked() {
        String nombre_negocio = txtNombreNegocio.getText().toString().trim();

        if (nombre_negocio.isEmpty()){
            tilNegocio.setError("Debe ingresar el nombre del negocio");
        } else {
            tilNegocio.setErrorEnabled(false);
            negocio.setNombreNegocio(nombre_negocio);
            negocio.insert();
            Intent intent = new Intent(RegistroInicial.this, RegistroUsuario.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.txtInicioSesion)
    public void onViewClickedSesion() {
        Intent intent = new Intent(RegistroInicial.this, Login.class);
        startActivity(intent);
    }
}
