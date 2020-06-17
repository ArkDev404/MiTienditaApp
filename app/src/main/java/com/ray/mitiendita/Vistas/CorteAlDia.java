package com.ray.mitiendita.Vistas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Gastos;
import com.ray.mitiendita.Modelos.Gastos_Table;
import com.ray.mitiendita.Modelos.TotalDeuda;
import com.ray.mitiendita.Modelos.TotalGastos;
import com.ray.mitiendita.Modelos.Ventas;
import com.ray.mitiendita.Modelos.Ventas_Table;
import com.ray.mitiendita.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CorteAlDia extends AppCompatActivity {

    @BindView(R.id.textVentas)
    TextView textVentas;
    @BindView(R.id.txtClientes)
    TextView txtClientes;
    @BindView(R.id.txtGastos)
    TextView txtGastos;

    private TotalDeuda totalDeuda;
    private TotalGastos totalGastos;


    DecimalFormat format = new DecimalFormat("$#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corte_al_dia);
        ButterKnife.bind(this);

        setVentasPagadas();
        setVentasNoPagadas();
        setGastosRealizados();
    }

    private void setVentasPagadas() {

        totalDeuda = SQLite.select(Method
                .sum(Ventas_Table.totalVenta)
                .as(Ventas.TOTAL))
                .from(Ventas.class)
                .where(Ventas_Table.EstaPagada.is("Si"))
                .queryCustomSingle(TotalDeuda .class);

        textVentas.setText(format.format(totalDeuda.totalDeuda));
    }

    private void setVentasNoPagadas() {
        totalDeuda = SQLite.select(Method
                .sum(Ventas_Table.totalVenta)
                .as(Ventas.TOTAL))
                .from(Ventas.class)
                .where(Ventas_Table.EstaPagada.is("No"))
                .queryCustomSingle(TotalDeuda.class);

        txtClientes.setText(format.format(totalDeuda.totalDeuda));
    }


    private void setGastosRealizados(){
        totalGastos = SQLite.select(Method
                .sum(Gastos_Table.monto)
                .as(Gastos.TOTAL))
                .from(Gastos.class)
                .queryCustomSingle(TotalGastos.class);

        txtGastos.setText(format.format(totalGastos.totalGastos));
    }

}
