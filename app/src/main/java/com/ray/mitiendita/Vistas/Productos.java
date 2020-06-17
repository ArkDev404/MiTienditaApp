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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorCategorias;
import com.ray.mitiendita.Adaptadores.AdaptadorProducto;
import com.ray.mitiendita.Listeners.OnItemCategoriaClickListener;
import com.ray.mitiendita.Listeners.OnItemClickListener;
import com.ray.mitiendita.Modelos.AppPreferences;
import com.ray.mitiendita.Modelos.AppPreferences_Table;
import com.ray.mitiendita.Modelos.Categorias;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.Modelos.Producto_Table;
import com.ray.mitiendita.R;
import com.thecode.aestheticdialogs.AestheticDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Productos extends AppCompatActivity implements OnItemClickListener, OnItemCategoriaClickListener {

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
    @BindView(R.id.recyclerCategorias)
    RecyclerView recyclerCategorias;

    private AdaptadorProducto adaptador;
    private AdaptadorCategorias adaptadorCategorias;
    private AppPreferences appPreferences = new AppPreferences();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        configAdaptador();
        configRecyclerView();
        generarCategorias();

        List<AppPreferences> appPreferencesList =
                SQLite.select().from(AppPreferences.class).queryList();

        if (appPreferencesList.get(0).getIsProducto()==0){
            crearShowCase();
        }

    }

    private void crearShowCase() {

        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fab),
                "Alta de Producto","Al dar clic en este boton puedes dar de alta," +
                        "nuevos productos")
                        .outerCircleColor(R.color.color_Primary)
                        .outerCircleAlpha(0.8f)
                        .targetCircleColor(R.color.md_white_1000)
                        .titleTextSize(25)
                        .titleTextColor(R.color.md_white_1000)
                        .descriptionTextSize(15)
                        .descriptionTextColor(R.color.md_white_1000)
                        .dimColor(R.color.md_black_1000)
                        .drawShadow(true)
                        .cancelable(false)
                        .tintTarget(false)
                        .transparentTarget(true)
                        .targetRadius(30),
                new TapTargetView.Listener(){
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        appPreferences = SQLite.select().from(AppPreferences.class)
                                .where(AppPreferences_Table.id.is(1)).querySingle();

                        appPreferences.setIsProducto(1);

                        appPreferences.update();
                    }
                });

    }


    private void generarCategorias() {
        int[] fotoCategorias = {R.drawable.ic_fruit,R.drawable.ic_drink,R.drawable.ic_quimico,
                R.drawable.ic_medicine,R.drawable.ic_milk,R.drawable.ic_carnes,R.drawable.ic_candy,
                R.drawable.ic_others};
        String[] nombreCategorias = {"Frutas","Bebidas","Quimicos","Medicina","Lacteos","Carnes","Dulces","Otros"};

        for (int i = 0; i < 8; i++) {
            Categorias categorias = new Categorias(fotoCategorias[i],nombreCategorias[i]);
            adaptadorCategorias.agregar(categorias);
        }

    }

    private void configRecyclerView() {
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProductos.setAdapter(adaptador);

        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerCategorias.setAdapter(adaptadorCategorias);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorProducto(new ArrayList<>(), this);
        adaptadorCategorias = new AdaptadorCategorias(new ArrayList<Categorias>(),this);
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
     * @return se regresa el resultado de la consulta
     */
    private List<Producto> getUsuariosDB() {
        return SQLite
                .select()
                .from(Producto.class)
                .queryList();
    }

    private List<Producto> getProductoCategoria(String categoria) {
        return SQLite
                .select()
                .from(Producto.class)
                .where(Producto_Table.Categoria.is(categoria))
                .queryList();
    }

    @OnClick(R.id.fab)
    public void agregarProducto() {
        Intent intent = new Intent(getApplicationContext(), AgregarProducto.class);
        startActivity(intent);
    }

    /**
     * Implementacion de metodos en el adaptador Productos
     */
    @Override
    public void onItemClick(Producto producto) {
        Intent intent = new Intent(this, DetalleProducto.class);
        intent.putExtra(Producto.ID, producto.getIdProducto());
        startActivity(intent);
    }

    @Override
    public void ontItemLongClick(Producto producto) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(600);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Esta seguro de eliminar este producto?")
                .setPositiveButton("Si", (dialog, which) -> {
                    try {
                        producto.delete();
                        adaptador.remover(producto);
                    } catch (Exception e) {
                        Log.e("Excepcion: ", e.getMessage());
                    }
                    AestheticDialog.showRainbow(Productos.this, "Eliminado",
                            "Se ha eliminado el producto exitosamente",
                            AestheticDialog.INFO);
                    validarVistas();
                })
                .setNegativeButton("Cancelar", null);
        builder.show();
    }

    /**
     * Implementacion del adaptador de Productos
     * @param categorias
     */
    @Override
    public void onItemClick(Categorias categorias) {
        Toast.makeText(this, "Consultando "+categorias.getNombreCategoria(),
                Toast.LENGTH_SHORT).show();
        adaptador.setList(getProductoCategoria(categorias.getNombreCategoria()));
        validarVistas();
    }
}
