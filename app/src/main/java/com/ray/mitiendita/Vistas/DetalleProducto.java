package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.Modelos.Producto_Table;
import com.ray.mitiendita.R;
import com.thecode.aestheticdialogs.AestheticDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalleProducto extends AppCompatActivity {

    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.delete_img)
    AppCompatImageView deleteImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etCodigoBarras)
    TextInputEditText etCodigoBarras;
    @BindView(R.id.etNombreProducto)
    TextInputEditText etNombreProducto;
    @BindView(R.id.etPrecio)
    TextInputEditText etPrecio;
    @BindView(R.id.etExistencias)
    TextInputEditText etExistencias;
    @BindView(R.id.etDescripcion)
    TextInputEditText etDescripcion;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    private Producto producto;
    private static final int REQUEST_CAMERA_PICKER = 21;
    private boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recibirDatoProducto(getIntent());
        configTituloToolbar();
        configImageView(producto.getFotoProducto());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configTituloToolbar() {
        toolbarLayout.setTitle(producto.getNombreProducto());
    }

    private void recibirDatoProducto(Intent intent) {
        getProducto(intent.getIntExtra(Producto.ID, 0));

        etCodigoBarras.setText(producto.getCodigoBarras());
        etNombreProducto.setText(producto.getNombreProducto());
        etPrecio.setText(String.valueOf(producto.getPrecio()));
        etExistencias.setText(String.valueOf(producto.getExistencias()));
        etDescripcion.setText(producto.getDescripcion());

    }


    private void configImageView(String fotoUrl) {
        if (fotoUrl != null) {
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(this)
                    .load(fotoUrl)
                    .apply(options)
                    .into(imgFoto);
        } else {
            imgFoto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual));
        }
        producto.setFotoProducto(fotoUrl);
    }

    private boolean validacionCampos() {

        boolean validado = true;

        String codigo = etCodigoBarras.getText().toString().trim();
        String nombre = etNombreProducto.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();
        String existencia = etExistencias.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (codigo.isEmpty()) {
            etCodigoBarras.setError("Debes rellenar este campo");
        }
        if (nombre.isEmpty()) {
            etNombreProducto.setError("Debes rellenar este campo");
        }
        if (descripcion.isEmpty()) {
            etDescripcion.setError("Debes rellenar este campo");
        }

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() &&
                !existencia.isEmpty() && !descripcion.isEmpty()) {
            validado = true;
        } else {
            validado = false;
        }

        if (precio.isEmpty() || Float.valueOf(etPrecio.getText().toString().trim()) < 0) {
            etPrecio.setError("El campo debe ser mayor a 0");
            validado = false;
        }
        if (existencia.isEmpty() || Integer.valueOf(etExistencias.getText().toString().trim()) < 0) {
            etExistencias.setError("El campo debe ser mayor a 0");
            validado = false;
        }

        return validado;
    }

    private void getProducto(int id) {
        producto = SQLite
                .select()
                .from(Producto.class)
                .where(Producto_Table.idProducto.is(id))
                .querySingle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_PICKER) {
                guardarFotoProducto(data.getDataString());
            }
        }

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            } else {
                etCodigoBarras.setText(intentResult.getContents().toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void guardarFotoProducto(String dataString) {
        producto.setFotoProducto(dataString);
        configImageView(dataString);
        Toast.makeText(this, "Imagen agregada", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.imgFoto, R.id.delete_img})
    public void onImageOperations(View view) {
        switch (view.getId()) {
            case R.id.imgFoto:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.txt_opcion)), REQUEST_CAMERA_PICKER);
                break;
            case R.id.delete_img:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Eliminar Foto")
                        .setMessage("¿Esta seguro de eliminar la foto?")
                        .setPositiveButton("Si", (dialog, which) -> {
                            guardarFotoProducto(null);
                            Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null);
                builder.show();
                break;
        }
    }

    private void habilitarCampos(boolean habilitado) {
        etCodigoBarras.setEnabled(habilitado);
        etNombreProducto.setEnabled(habilitado);
        etPrecio.setEnabled(habilitado);
        etExistencias.setEnabled(habilitado);
        etDescripcion.setEnabled(habilitado);
        //imgFoto.setEnabled(true);
    }

    @OnClick(R.id.fab)
    public void habilitarOActualizar() {
        if (editable){
        if (validacionCampos()){
            producto.setCodigoBarras(etCodigoBarras.getText().toString().trim());
            producto.setNombreProducto(etNombreProducto.getText().toString().trim());
            producto.setDescripcion(etDescripcion.getText().toString().trim());
            producto.setPrecio(Float.valueOf(etPrecio.getText().toString().trim()));
            producto.setExistencias(Integer.valueOf(etExistencias.getText().toString().trim()));
            producto.setFotoProducto(producto.getFotoProducto());
            try {
                producto.update();
                configTituloToolbar();
                AestheticDialog.showToaster(this, "Actualización",
                        "Se actualizo el producto exitosamente",AestheticDialog.INFO);
            } catch (Exception e){
                Log.e("SQL: ", e.getMessage());
            }
            }
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_edit));
            habilitarCampos(false);
            editable = false;
        } else {
            editable = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_save));
            habilitarCampos(true);
        }
    }
}
