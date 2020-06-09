package com.ray.mitiendita.Vistas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.Modelos.Cliente_Table;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalleCliente extends AppCompatActivity {

    @BindView(R.id.etNombreCliente)
    TextInputEditText etNombreCliente;
    @BindView(R.id.etApellidosCliente)
    TextInputEditText etApellidosCliente;
    @BindView(R.id.etSexo)
    AutoCompleteTextView etSexo;
    @BindView(R.id.tvSaldo)
    MaterialTextView tvSaldo;
    private Cliente cliente;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);
        ButterKnife.bind(this);
        cargarCombobox();
        recibirDatoCliente(getIntent());
        etSexo.setOnTouchListener((v, event) -> {
            etSexo.setText("");
            return false;
        });
    }

    private void cargarCombobox() {
        String[] sexo = new String[]{"Hombre", "Mujer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sexo, sexo);
        etSexo.setAdapter(adapter);

    }

    private void recibirDatoCliente(Intent intent) {
        getCliente(intent.getIntExtra(Cliente.ID, 0));

        int id = cliente.getIdCliente();
        etNombreCliente.setText(cliente.getNombre());
        etApellidosCliente.setText(cliente.getApellidos());
        etSexo.setText(cliente.getSexo());
        tvSaldo.setText(String.valueOf(cliente.getSaldo()));

    }

    private void getCliente(int id) {
        cliente = SQLite
                .select()
                .from(Cliente.class)
                .where(Cliente_Table.idCliente.is(id))
                .querySingle();
    }

    private boolean validarCampos() {
        boolean validado = true;
        String nombre = etNombreCliente.getText().toString().trim();
        String sexo = etSexo.getText().toString().trim();

        if (nombre.isEmpty()) {
            etNombreCliente.setError("Este campo es obligatorio");
            validado = false;
        } else {
            validado = true;
        }

        if (sexo.isEmpty()) {
            etSexo.setError("Debe asignarle sexo al cliente");
            validado = false;
        } else {
            validado = true;
        }
        return validado;
    }

    @OnClick(R.id.tvEditar)
    public void onViewClicked() {
        if (validarCampos()) {
            cliente.setNombre(etNombreCliente.getText().toString().trim());
            cliente.setApellidos(etApellidosCliente.getText().toString().trim());
            cliente.setSexo(etSexo.getText().toString().trim());
            cliente.setSaldo(Float.valueOf(tvSaldo.getText().toString().trim()));

            try {
                Log.e("SQL: ", "Datos editados exitosamente");
                cliente.update();
                finish();
            } catch (Exception e) {
                Log.e("SQL: ", e.getMessage());
            }
        }
    }

    @OnClick(R.id.btn_detalleS)
    public void detalleSaldo() {
        Intent intent = new Intent(this, DetalleSaldoCliente.class);
        intent.putExtra(Cliente.ID,cliente.getIdCliente());
        startActivity(intent);
    }
}
