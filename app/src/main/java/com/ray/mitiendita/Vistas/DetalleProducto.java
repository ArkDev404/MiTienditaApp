package com.ray.mitiendita.Vistas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.Modelos.Producto_Table;
import com.ray.mitiendita.R;
import com.thecode.aestheticdialogs.AestheticDialog;

import java.io.File;

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
    @BindView(R.id.til_CodigoBarras)
    TextInputLayout tilCodigoBarras;
    @BindView(R.id.til_NombreProducto)
    TextInputLayout tilNombreProducto;
    @BindView(R.id.til_Precio)
    TextInputLayout tilPrecio;
    @BindView(R.id.til_Existencias)
    TextInputLayout tilExistencias;
    @BindView(R.id.til_Descripcion)
    TextInputLayout tilDescripcion;
    @BindView(R.id.etCategoria)
    AutoCompleteTextView etCategoria;

    private Producto producto;

    private static final int REQUEST_CAMERA_PICKER = 21;
    private static final int COD_FOTO = 22 ;


    private final String CARPETA_RAIZ = "MiTiendita/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ+"Productos";

    String path;

    private boolean editable;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recibirDatoProducto(getIntent());
        configTituloToolbar();
        cargarCombobox();
        etCodigoBarras.setOnTouchListener((v, event) -> {
            leerCodigoBarras();
            return false;
        });
        configImageView(producto.getFotoProducto());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void leerCodigoBarras() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    private void configTituloToolbar() {
        toolbarLayout.setTitle(producto.getNombreProducto());
        toolbarLayout.setExpandedTitleColor(getColor(R.color.md_white_1000));
        toolbarLayout.setCollapsedTitleTextColor(getColor(R.color.md_white_1000));
    }

    private void recibirDatoProducto(Intent intent) {
        getProducto(intent.getIntExtra(Producto.ID, 0));

        etCodigoBarras.setText(producto.getCodigoBarras());
        etNombreProducto.setText(producto.getNombreProducto());
        etPrecio.setText(String.valueOf(producto.getPrecio()));
        etCategoria.setText(producto.getCategoria());
        etExistencias.setText(String.valueOf(producto.getExistencias()));
        etDescripcion.setText(producto.getDescripcion());

    }

    private void cargarCombobox() {
        String[] categorias = new String[]{"Frutas","Bebidas","Quimicos","Medicina","Lacteos","Carnes","Dulces","Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sexo, categorias);
        etCategoria.setAdapter(adapter);

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
            tilCodigoBarras.setError("Debes rellenar este campo");
        } else {
            tilCodigoBarras.setErrorEnabled(false);
        }
        if (nombre.isEmpty()) {
            tilNombreProducto.setError("Debes rellenar este campo");
        } else {
            tilNombreProducto.setErrorEnabled(false);
        }
        if (descripcion.isEmpty()) {
            tilDescripcion.setError("Debes rellenar este campo");
        } else {
            tilDescripcion.setErrorEnabled(false);
        }

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() &&
                !existencia.isEmpty() && !descripcion.isEmpty()) {
            validado = true;
        } else {
            validado = false;
        }

        if (precio.isEmpty() || Float.valueOf(etPrecio.getText().toString().trim()) < 0) {
            tilPrecio.setError("El campo debe ser mayor a 0");
            validado = false;
        }
        if (existencia.isEmpty() || Integer.valueOf(etExistencias.getText().toString().trim()) < 0) {
            tilExistencias.setError("El campo debe ser mayor a 0");
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

            if (requestCode == COD_FOTO){
                configImageView(path);
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
                final CharSequence[] opciones = {"Tomar Foto","Cargar Imagen"};
                AlertDialog.Builder builderOpciones = new AlertDialog.Builder(this)
                        .setTitle("Selecciona una opcion")
                        .setItems(opciones, (dialog, which) -> {
                            if (opciones[which].equals("Tomar Foto")){
                                File fileImagen = new File (Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
                                boolean isCreada = fileImagen.exists();
                                String nombre="";

                                if (isCreada == false) {
                                    isCreada=fileImagen.mkdirs();
                                }
                                if (isCreada == true) {
                                    nombre = (System.currentTimeMillis()/1000)+".jpg";
                                }

                                path = Environment.getExternalStorageDirectory()+
                                        File.separator+RUTA_IMAGEN+File.separator+nombre;

                                File imagen = new File(path);

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                                {
                                    String authorities=getApplicationContext().getPackageName()+".provider";
                                    Uri imageUri= FileProvider.getUriForFile(getApplicationContext(),authorities,imagen);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                }else
                                {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
                                }
                                startActivityForResult(intent,COD_FOTO);
                            } else if (opciones[which].equals("Cargar Imagen")){
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                                startActivityForResult(Intent.createChooser(intent,
                                        getString(R.string.txt_opcion)), REQUEST_CAMERA_PICKER);
                            }
                        });
                builderOpciones.show();

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
        etCategoria.setEnabled(habilitado);
        etExistencias.setEnabled(habilitado);
        etDescripcion.setEnabled(habilitado);
        etCategoria.setEnabled(habilitado);
        //imgFoto.setEnabled(true);
    }

    @OnClick(R.id.fab)
    public void habilitarOActualizar() {
        if (editable) {
            if (validacionCampos()) {
                producto.setCodigoBarras(etCodigoBarras.getText().toString().trim());
                producto.setNombreProducto(etNombreProducto.getText().toString().trim());
                producto.setCategoria(etCategoria.getText().toString().trim());
                producto.setDescripcion(etDescripcion.getText().toString().trim());
                producto.setPrecio(Float.valueOf(etPrecio.getText().toString().trim()));
                producto.setExistencias(Integer.valueOf(etExistencias.getText().toString().trim()));
                producto.setFotoProducto(producto.getFotoProducto());
                try {
                    producto.update();
                    configTituloToolbar();
                    AestheticDialog.showRainbow(this, "Actualización",
                            "Se actualizo el producto exitosamente", AestheticDialog.INFO);
                } catch (Exception e) {
                    Log.e("SQL: ", e.getMessage());
                }
            }
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit));
            habilitarCampos(false);
            editable = false;
        } else {
            editable = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_save));
            habilitarCampos(true);
        }
    }
}
