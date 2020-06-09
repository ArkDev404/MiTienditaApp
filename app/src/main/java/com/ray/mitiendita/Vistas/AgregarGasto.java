package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Gastos;
import com.ray.mitiendita.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregarGasto extends AppCompatActivity {

    @BindView(R.id.tv_folioGasto)
    TextView tvFolioGasto;
    @BindView(R.id.txtFecha)
    TextView txtFecha;
    @BindView(R.id.etMotivo)
    TextInputEditText etMotivo;
    @BindView(R.id.etMonto)
    TextInputEditText etMonto;
    @BindView(R.id.fotoDocumento)
    AppCompatImageView fotoDocumento;

    private static final int REQUEST_CAMERA_PICKER = 21;
    Gastos gastos = new Gastos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_gasto);
        ButterKnife.bind(this);
        setIdGasto();
        cargarFecha();
    }


    private void cargarFecha() {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String salida = sdf.format(d);
        txtFecha.setText(salida);
    }

    private void setIdGasto() {
        try {
            long noVentas = 1 + SQLite.
                    selectCountOf().
                    from(Gastos.class).
                    count();

            tvFolioGasto.setText(String.valueOf(noVentas));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void configImageView(String fotoUrl) {
        if (fotoUrl != null) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(this)
                    .load(fotoUrl)
                    .apply(options)
                    .into(fotoDocumento);
        } else {
            fotoDocumento.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual));
        }
        gastos.setComprobanteGasto(fotoUrl);
    }


    private void guardarFoto(String dataString) {
        gastos.setComprobanteGasto(dataString);
        configImageView(dataString);
        Toast.makeText(this, "Imagen agregada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_PICKER) {
                guardarFoto(data.getDataString());
            }
        }
    }

    @OnClick(R.id.btnFoto)
    public void onOperacionesFoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_opcion)), REQUEST_CAMERA_PICKER);
    }

    @OnClick(R.id.btnGuardar)
    public void onGuardar() {
        gastos.setMotivoGasto(etMotivo.getText().toString().trim());
        gastos.setMonto(Float.valueOf(etMonto.getText().toString().trim()));
        gastos.setFechaGasto(txtFecha.getText().toString().trim());
        gastos.setComprobanteGasto(gastos.getComprobanteGasto());

        try {
            Log.e("SQL: ", "Inserción Exitosa");
            Toast.makeText(this, gastos.getComprobanteGasto(), Toast.LENGTH_SHORT).show();
            gastos.insert();
            finish();
        } catch (Exception e) {
            Log.e("SQL: ", e.getMessage());
        }
    }
}
