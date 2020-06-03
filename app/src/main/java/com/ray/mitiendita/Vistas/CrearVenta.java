package com.ray.mitiendita.Vistas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorListaP;
import com.ray.mitiendita.Listeners.OnItemProductListClickListener;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.Modelos.Cliente_Table;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.Modelos.DetalleVentas_Table;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.Modelos.Producto_Table;
import com.ray.mitiendita.Modelos.TotalVentas;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrearVenta extends AppCompatActivity implements OnItemProductListClickListener {

    @BindView(R.id.txtFecha)
    TextView txtFecha;
    @BindView(R.id.etNombreProducto)
    TextInputEditText etNombreProducto;
    @BindView(R.id.etPrecio)
    TextInputEditText etPrecio;
    @BindView(R.id.tv_folio)
    TextView tvFolio;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etCantidad)
    TextInputEditText etCantidad;
    @BindView(R.id.recyclerProductosVendidos)
    RecyclerView recyclerProductosVendidos;
    @BindView(R.id.tv_total)
    MaterialTextView tvTotal;
    @BindView(R.id.etCliente)
    AutoCompleteTextView etCliente;
    @BindView(R.id.switch_pago)
    SwitchMaterial switchPago;

    private Producto producto;
    private TotalVentas totalVentas;
    private Cliente cliente;
    private Ventas ventas = new Ventas();
    private DetalleVentas detalleVentas = new DetalleVentas();

    AdaptadorListaP adaptador;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_venta);
        ButterKnife.bind(this);
        cargarFecha();
        configAdaptador();
        configRecyclerView();
        setIdVenta();
        rellenarComboCliente();
        adaptador.setList(getDetalleVentaDB());

        etNombreProducto.setOnTouchListener((v, event) -> {
            leerCodigoBarras();
            return false;
        });
    }

    /**
     * Este metodo permite rellenar con una consulta el AutoCompleteTextView
     */
    private void rellenarComboCliente() {
        try {

            List<Cliente> clientes = SQLite.select()
                    .from(Cliente.class)
                    .queryList();

            List<String> nombresCliente = new ArrayList<>();


            for (int i = 0; i < clientes.size(); i++) {
                //clientes.get(i).getNombre() + " " + clientes.get(i).getApellidos()
                nombresCliente.add(clientes.get(i).getNombre());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sexo, nombresCliente);
            etCliente.setAdapter(adapter);


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Este metodo permite totalizar los articulos que esten en una venta
     * Equivalencia en SQL:
     * Select SUM(cantidad * precio) as total
     * From DetalleVentas
     * Where folioVentas = x valor
     */
    private void setTotalVenta() {
        try {
            int folio = Integer.valueOf(tvFolio.getText().toString());
            totalVentas = SQLite.select(Method
                    .sum(DetalleVentas_Table.Cantidad.times(DetalleVentas_Table.Precio))
                    .as(DetalleVentas.TOTAL))
                    .from(DetalleVentas.class)
                    .where(DetalleVentas_Table.folioVenta.is(folio))
                    .queryCustomSingle(TotalVentas.class);

            float total = totalVentas.total;

            tvTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este metodo permite traer el folio de la venta, y al resultado se le suma 1
     * Equivalente en SQL - Select count(*) + 1 From Ventas
     */
    private void setIdVenta() {
        try {
            long noVentas = 1 + SQLite.
                    selectCountOf().
                    from(Ventas.class).
                    count();

            tvFolio.setText(String.valueOf(noVentas));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void configRecyclerView() {
        recyclerProductosVendidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProductosVendidos.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorListaP(new ArrayList<>(), this);

    }

    /**
     * En SQL seria Select * From DetalleVentas Where folioVenta = x valor
     *
     * @return Se retorna rel resultado de la consulta
     */
    private List<DetalleVentas> getDetalleVentaDB() {
        return SQLite
                .select()
                .from(DetalleVentas.class)
                .where(DetalleVentas_Table.folioVenta.is(Integer.valueOf(tvFolio.getText().toString())))
                .queryList();
    }

    /**
     * Este metodo permite cargar la fecha actual
     */
    private void cargarFecha() {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String salida = sdf.format(d);
        txtFecha.setText(salida);
    }

    /**
     * Con este metodo hacemos uso de la libreria para Escanear el codigo de barras
     */
    private void leerCodigoBarras() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    // Todo: Variable provisional
                    double factorUtilidad = 1.1;
                    String codigoEscaneado = intentResult.getContents();
                    getInfoProducto(codigoEscaneado);
                    etNombreProducto.setText(producto.getNombreProducto());
                    etPrecio.setText(String.valueOf(producto.getPrecio() * factorUtilidad));
                } catch (Exception e) {
                    Toast.makeText(this, "No hay productos con este Codigo", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Se genera una consulta que obtenga los datos de los productos
     *
     * @param codigoEscaneado
     */
    private void getInfoProducto(String codigoEscaneado) {
        producto = SQLite
                .select()
                .from(Producto.class)
                .where(Producto_Table.CodigoBarras.is(codigoEscaneado))
                .querySingle();
    }


    @OnClick(R.id.btn_agregar_al_carrito)
    public void agregarProducto() {
        agregarArticulo();
        adaptador.setList(getDetalleVentaDB());
        setTotalVenta();
    }

    private void agregarArticulo() {
        try {
            detalleVentas.setFolioVenta(Integer.valueOf(tvFolio.getText().toString().trim()));
            detalleVentas.setProductoVendido(etNombreProducto.getText().toString().trim());
            detalleVentas.setPrecio(Float.valueOf(etPrecio.getText().toString().trim()));
            detalleVentas.setCantidad(Integer.valueOf(etCantidad.getText().toString().trim()));
            detalleVentas.insert();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Al presionar el boton atras, se hace una notificacion de si esta seguro de cancelar la venta
     */
    @Override
    public void onBackPressed() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            Toast.makeText(this, "Venta Cancelada", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_info_blue_24dp)
                    .setTitle("Cancelar Venta")
                    .setMessage("Esta seguro de cancelar la venta")
                    .setPositiveButton("Si", (dialog, which) -> {
                        Intent intent = new Intent(this, ListaVentas.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, "Venta cancelada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null);
            builder.show();
        }

    }

    @OnClick(R.id.btn_pagar)
    public void pagarVenta() {
        int items = adaptador.getItemCount();
        if (items == 0) {
           Toast.makeText(this, "No hay productos registrados", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Venta")
                    .setMessage("¿Esta seguro de finalizar la venta?")
                    .setPositiveButton("Si", (dialog, which) -> {
                        try {
                            cliente = SQLite.
                                    select().
                                    from(Cliente.class).
                                    where(Cliente_Table.Nombre.is(etCliente.getText().toString())).
                                    querySingle();

                            int id = cliente.getIdCliente();
                            String isPagado;

                            if (switchPago.isChecked()){ isPagado = "Si"; } else { isPagado = "No"; }

                            ventas.setIdFolio(Integer.valueOf(tvFolio.getText().toString().trim()));
                            ventas.setFechaVenta(txtFecha.getText().toString().trim());
                            ventas.setEstaPagada(isPagado);
                            ventas.setIdCliente(id);
                            ventas.insert();
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null);
            builder.show();
        }

    }

    /*Metodos implementados*/
    @Override
    public void onItemClick(DetalleVentas detalleVentas) {

    }

    @Override
    public void onItemLongClick(DetalleVentas detalleVentas) {

    }

}
