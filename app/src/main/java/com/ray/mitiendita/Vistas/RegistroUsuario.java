package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroUsuario extends AppCompatActivity {

    @BindView(R.id.btnCredSig)
    MaterialButton btnCredSig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCredSig)
    public void onViewClicked() {
        Intent intent = new Intent(RegistroUsuario.this,OnBoardingScreen.class);
        startActivity(intent);
    }
}
