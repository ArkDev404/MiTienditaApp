package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ray.mitiendita.Modelos.Usuarios;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroUsuario extends AppCompatActivity {

    @BindView(R.id.btnCredSig)
    MaterialButton btnCredSig;
    @BindView(R.id.txtUser)
    TextInputEditText txtUser;
    @BindView(R.id.til_usuario)
    TextInputLayout tilUsuario;
    @BindView(R.id.txtContra)
    TextInputEditText txtContra;
    @BindView(R.id.til_contrasena)
    TextInputLayout tilContrasena;

    private Usuarios usuarios = new Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCredSig)
    public void onViewClicked() {

        String n_usuario = txtUser.getText().toString().trim();
        String contraseña = txtContra.getText().toString().trim();

        if (n_usuario.isEmpty()) {
            tilUsuario.setError("Debe ingresar un nombre de usuario");
        } else {
            tilUsuario.setErrorEnabled(false);
        }

        if (contraseña.isEmpty()){
            tilContrasena.setError("Debe ingresar una contraseña");
        } else {
            tilContrasena.setErrorEnabled(false);
        }

        if (!n_usuario.isEmpty() && !contraseña.isEmpty()){
            usuarios.setNombreUsuario(n_usuario);
            usuarios.setContraseña(contraseña);
            usuarios.setActivo(1); //Asignamos al usuario como activo para validar sesión
            usuarios.insert();
            Intent intent = new Intent(RegistroUsuario.this, OnBoardingScreen.class);
            startActivity(intent);
            finish();
        }
    }
}
