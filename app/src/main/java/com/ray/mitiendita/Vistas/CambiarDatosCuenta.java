package com.ray.mitiendita.Vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Usuarios;
import com.ray.mitiendita.Modelos.Usuarios_Table;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CambiarDatosCuenta extends AppCompatActivity {

    @BindView(R.id.txtContraActual)
    TextInputEditText txtContraActual;
    @BindView(R.id.til_ContraA)
    TextInputLayout tilContraA;
    @BindView(R.id.txtNuevaContra)
    TextInputEditText txtNuevaContra;
    @BindView(R.id.til_nuevaC)
    TextInputLayout tilNuevaC;

    private Usuarios usuarios = new Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_datos_cuenta);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnAceptar)
    public void onViewClicked() {

        String contraActual = txtContraActual.getText().toString().trim();
        String nuevaContra = txtNuevaContra.getText().toString().trim();

        if (contraActual.isEmpty()){
            tilContraA.setError("Debe ingresar su contraseña actual");
        } else {
            tilContraA.setErrorEnabled(false);
        }

        if (nuevaContra.isEmpty()){
            tilNuevaC.setError("Debe ingresar su nueva contraseña");
        } else {
            tilNuevaC.setErrorEnabled(false);
        }

        usuarios = SQLite.select().from(Usuarios.class).where(Usuarios_Table.id_Usuario.is(1))
                .querySingle();


        if (contraActual.equals(usuarios.getContraseña())){
            if (!nuevaContra.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Cambio de Contraseña")
                        .setMessage("¿Esta seguro de cambiar de contraseña? " +
                                "Esta sera tu contraseña a partir de hoy")
                        .setPositiveButton("Si", (dialog, which) -> {
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            usuarios.setContraseña(nuevaContra);
                            usuarios.update();
                            startActivity(intent);
                            Toast.makeText(CambiarDatosCuenta.this, "Se ha cambiado su contraseña",
                                    Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) ->
                                Toast.makeText(CambiarDatosCuenta.this, "Operacion Cancelada",
                                        Toast.LENGTH_SHORT).show());
                builder.show();
            }
        } else {
            Toast.makeText(this, "La contraseña actual ingresada no es correcta!", Toast.LENGTH_SHORT).show();
        }

    }
}
