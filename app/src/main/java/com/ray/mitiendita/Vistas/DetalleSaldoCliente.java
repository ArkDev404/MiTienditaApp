package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Adaptadores.AdaptadorVentas;
import com.ray.mitiendita.Listeners.OnItemVentaClickListener;
import com.ray.mitiendita.Modelos.Abonos;
import com.ray.mitiendita.Modelos.Abonos_Table;
import com.ray.mitiendita.Modelos.Cliente;
import com.ray.mitiendita.Modelos.Cliente_Table;
import com.ray.mitiendita.Modelos.DetalleVentas;
import com.ray.mitiendita.Modelos.TotalAbonos;
import com.ray.mitiendita.Modelos.TotalDeuda;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.Modelos.Ventas_Table;
import com.ray.mitiendita.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalleSaldoCliente extends AppCompatActivity implements OnItemVentaClickListener {

    int idClienteDeudor;
    private AdaptadorVentas adaptador;

    private Cliente cliente;
    private Abonos abonos = new Abonos();
    private TotalAbonos totalAbonos;
    private TotalDeuda totalDeuda;

    DecimalFormat format = new DecimalFormat("#,###.00");


    @BindView(R.id.recyclerVentasCliente)
    RecyclerView recyclerVentasCliente;
    @BindView(R.id.vistaVacia)
    LinearLayout vistaVacia;
    @BindView(R.id.tv_deuda)
    MaterialTextView tvDeuda;
    @BindView(R.id.tv_abonos)
    MaterialTextView tvAbonos;
    @BindView(R.id.tv_total)
    MaterialTextView tvTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_saldo_cliente);
        ButterKnife.bind(this);
        configAdaptador();
        configRecyclerView();
        recibirIDCliente(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            setTotalDeudaCliente();
            setTotalAbonos();
            calcularTotal();
        } catch (Exception e) {
            Log.e( "SQL: ","Error lectura" );
        }

        adaptador.setList(getVentasCliente());
        validarVistas();
    }

    private void configRecyclerView() {
        recyclerVentasCliente.setLayoutManager(new LinearLayoutManager(this));
        recyclerVentasCliente.setAdapter(adaptador);
    }

    private void configAdaptador() {
        adaptador = new AdaptadorVentas(this, new ArrayList<>());
    }

    private void validarVistas() {
        int items = adaptador.getItemCount();
        if (items == 0) {
            vistaVacia.setVisibility(View.VISIBLE);
            recyclerVentasCliente.setVisibility(View.GONE);
        } else {
            vistaVacia.setVisibility(View.GONE);
            recyclerVentasCliente.setVisibility(View.VISIBLE);
        }
    }

    private void recibirIDCliente(Intent intent) {
        idClienteDeudor = intent.getIntExtra(Cliente.ID, 0);
    }


    private void calcularTotal(){

        float deuda = Float.valueOf(tvDeuda.getText().toString());
        float abonos = Float.valueOf(tvAbonos.getText().toString());

        float saldoTotal = deuda - abonos;

        tvTotal.setText(format.format(saldoTotal));
    }
    /**
     * Esta consulta equivale en SQL a:
     * Select * From Cliente inner join Ventas
     * on Clientes.idCliente = Ventas.idCliente
     * Where idCliente = x
     * Donde x es el id del cliente a consultar
     *
     * @return
     */

    private void setTotalAbonos() {
        totalAbonos = SQLite.select(Method
                .sum(Abonos_Table.montoAbono)
                .as(Abonos.TOTAL))
                .from(Abonos.class)
                .where(Abonos_Table.idClienteDeudor.is(idClienteDeudor))
                .queryCustomSingle(TotalAbonos.class);

        tvAbonos.setText(format.format(totalAbonos.totalAbonos));

    }

    private void setTotalDeudaCliente(){
        totalDeuda = SQLite.select(Method
                        .sum(Ventas_Table.totalVenta)
                        .as(Ventas.TOTAL))
                        .from(Ventas.class)
                        .where(Ventas_Table.ClienteID.is(idClienteDeudor),Ventas_Table.EstaPagada.is("No"))
                        .queryCustomSingle(TotalDeuda.class);
        
        tvDeuda.setText(format.format(totalDeuda.totalDeuda));
    }
    
    
    private List<Ventas> getVentasCliente() {
        return SQLite
                .select()
                .from(Ventas.class)
                .innerJoin(Cliente.class)
                .on(Cliente_Table.idCliente.withTable().eq(Ventas_Table.ClienteID.withTable()))
                .where(Ventas_Table.ClienteID.is(idClienteDeudor),Ventas_Table.EstaPagada.is("No"))
                .queryList();
    }

    @Override
    public void onItemClick(Ventas ventas) {
        Intent intent = new Intent(this, DetalleVenta.class);
        intent.putExtra(Ventas.ID,ventas.getIdFolio());
        startActivity(intent);
    }

    @OnClick(R.id.btn_abonar)
    public void onViewClicked() {

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        layoutParams.gravity = Gravity.CENTER;

        TextInputEditText editText = new TextInputEditText(this);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.setHint("Ingrese el monto");
        editText.setLayoutParams(layoutParams);

        linearLayout.addView(editText);
        linearLayout.setPadding(50, 0, 50, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Abono")
                .setMessage("Ingrese el monto de su abono")
                .setPositiveButton("Aceptar", (dialog, which) -> {

                    // Verificamos el cliente a afectar
                    cliente = SQLite
                            .select()
                            .from(Cliente.class)
                            .where(Cliente_Table.idCliente.is(idClienteDeudor))
                            .querySingle();

                    // Monto a restar al cliente
                    float Monto = Float.valueOf(editText.getText().toString().trim());

                    // Nuevo saldo
                    float nuevoSaldo = cliente.getSaldo() - Monto;

                    // Variable para obtener el valor del total
                    float total = Float.valueOf(tvTotal.getText().toString().trim());

                    if (Monto > total)
                    {

                        try {

                            float cambio = Monto - total;

                            // Actualizamos las ventas a pagadas
                            SQLite.update(Ventas.class)
                                    .set(Ventas_Table.EstaPagada.eq("Si"))
                                    .where(Ventas_Table.ClienteID.is(idClienteDeudor))
                                    .async()
                                    .execute();

                            // Eliminamos los abonos para restablecer el contador de abonos
                            SQLite.delete(Abonos.class)
                                    .where(Abonos_Table.idClienteDeudor.is(idClienteDeudor))
                                    .async()
                                    .execute();

                            // Notificamos el cambio
                            Toast.makeText(this, "Devuelve al cliente $" + cambio , Toast.LENGTH_SHORT).show();


                            //Afectación a clientes
                            cliente.setSaldo(0);

                            cliente.update();

                            finish();

                        } catch (Exception e){
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        try {

                            // Afectación a Abono
                            abonos.setIdClienteDeudor(idClienteDeudor);
                            abonos.setMontoAbono(Monto);

                            abonos.insert();

                            // Afectación a Cliente
                            cliente.setSaldo(nuevoSaldo);

                            cliente.update();

                            setTotalAbonos();

                            calcularTotal();

                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                })
                .setNegativeButton("Cancelar", null)
                .setView(linearLayout);
        builder.show();
    }
}
