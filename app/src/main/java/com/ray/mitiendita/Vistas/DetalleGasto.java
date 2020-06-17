package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Gastos;
import com.ray.mitiendita.Modelos.Gastos_Table;
import com.ray.mitiendita.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleGasto extends AppCompatActivity {

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

    private Gastos gastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gasto);
        ButterKnife.bind(this);

        recibirDatoGasto(getIntent());
        configImageView(gastos.getComprobanteGasto());
    }

    private void recibirDatoGasto(Intent intent) {
        getGasto(intent.getIntExtra(Gastos.ID,0));

        tvFolioGasto.setText(String.valueOf(gastos.getIdGasto()));
        txtFecha.setText(gastos.getFechaGasto());
        etMotivo.setText(gastos.getMotivoGasto());
        etMonto.setText(String.valueOf(gastos.getMonto()));
    }

    private void getGasto(int valor) {
        gastos = SQLite.select()
                .from(Gastos.class)
                .where(Gastos_Table.idGasto.is(valor))
                .querySingle();
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
}
