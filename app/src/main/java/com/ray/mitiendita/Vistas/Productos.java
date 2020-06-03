package com.ray.mitiendita.Vistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorProducto;
import com.ray.mitiendita.Listeners.OnItemClickListener;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.R;
import com.thecode.aestheticdialogs.AestheticDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Productos extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerProductos)
    RecyclerView recyclerProductos;
    @BindView(R.id.contenedorProductos)
    CoordinatorLayout contenedorProductos;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private AdaptadorProducto adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        configAdaptador();
        configRecyclerView();

        //generarProductos();

    }

    private void configRecyclerView() {
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProductos.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorProducto(new
                ArrayList<Producto>(), this);
    }


    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerProductos.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerProductos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.setList(getUsuariosDB());
        validarVistas();
    }

    /**
     *
     * @return se regresa el resultado de la consulta
     */
    private List<Producto> getUsuariosDB() {
        return SQLite
                .select()
                .from(Producto.class)
                .queryList();
    }


    @OnClick(R.id.fab)
    public void agregarProducto() {
        Intent intent = new Intent(getApplicationContext(), AgregarProducto.class);
        startActivity(intent);
    }

    /**Implementacion de metodos en el adaptador*/
    @Override
    public void onItemClick(Producto producto) {
        Intent intent = new Intent(this, DetalleProducto.class);
        intent.putExtra(Producto.ID, producto.getIdProducto());
        startActivity(intent);
    }

    @Override
    public void ontItemLongClick(Producto producto) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator!=null) {
            vibrator.vibrate(600);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Esta seguro de eliminar este producto?")
                .setPositiveButton("Si", (dialog, which) -> {
                    try {
                        producto.delete();
                        adaptador.remover(producto);
                    } catch (Exception e){
                        Log.e("Excepcion: ",e.getMessage());
                    }
                    AestheticDialog.showToaster(Productos.this,"Eliminado",
                           "Se ha eliminado el producto exitosamente",
                              AestheticDialog.INFO);
                    validarVistas();
                })
                .setNegativeButton("Cancelar", null);
        builder.show();
    }

}