package com.ray.mitiendita.Vistas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AgregarCliente extends AppCompatActivity {


    @BindView(R.id.etSexo)
    AutoCompleteTextView etSexo;
    @BindView(R.id.etNombreCliente)
    TextInputEditText etNombreCliente;
    @BindView(R.id.etApellidosCliente)
    TextInputEditText etApellidosCliente;
    @BindView(R.id.etSaldo)
    TextInputEditText etSaldo;

    Cliente cliente = new Cliente();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);
        ButterKnife.bind(this);
        cargarCombobox();

    }

    /**
     * Se rellena el AutoCompleteTextView con los datos del recurso XML que contiene los sexos
     * Se evito el Spinner debido que carece de Material Design
     */
    private void cargarCombobox() {
        String[] sexo = new String[]{"Hombre", "Mujer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sexo, sexo);
        etSexo.setAdapter(adapter);

    }

    private boolean validarCampos(){
        boolean validado = true;
        String nombre = etNombreCliente.getText().toString().trim();
        String sexo = etSexo.getText().toString().trim();

        if (nombre.isEmpty()){
            etNombreCliente.setError("Este campo es obligatorio");
            validado = false;
        } else {
            validado = true;
        }

        if (sexo.isEmpty()){
            etSexo.setError("Debe asignarle sexo al cliente");
            validado = false;
        } else {
            validado = true;
        }
        return validado;
    }


    @OnClick(R.id.btnGuardar)
    public void onViewClicked() {

        if (validarCampos()){
            cliente.setNombre(etNombreCliente.getText().toString().trim());
            cliente.setApellidos(etApellidosCliente.getText().toString().trim());
            cliente.setSexo(etSexo.getText().toString().trim());
            cliente.setSaldo(Float.valueOf(etSaldo.getText().toString().trim()));

            try {
                Log.e("SQL: ", "Inserción Exitosa");
                cliente.insert();
                finish();
            } catch (Exception e) {
                Log.e("SQL: ", e.getMessage());
            }
        }
    }
}
