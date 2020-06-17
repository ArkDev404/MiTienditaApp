package com.ray.mitiendita.Vistas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ray.mitiendita.Modelos.Producto;
import com.ray.mitiendita.Modelos.Producto_Table;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class Estadisticas extends AppCompatActivity {

    @BindView(R.id.pieChart)
    PieChartView pieChart;
    @BindView(R.id.txtProducto1)
    MaterialTextView txtProducto1;
    @BindView(R.id.txtProducto2)
    MaterialTextView txtProducto2;
    @BindView(R.id.txtProducto3)
    MaterialTextView txtProducto3;
    @BindView(R.id.txtProducto4)
    MaterialTextView txtProducto4;
    @BindView(R.id.txtProducto5)
    MaterialTextView txtProducto5;

    private PieChartData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        ButterKnife.bind(this);

        cargarDatos();

    }


    /**
     * Probando libreria de HelloCharts con 5 Valores
     */
    private void cargarDatos() {
        List<Producto> productoList =
                SQLite
                        .select()
                        .from(Producto.class)
                        .orderBy(Producto_Table.Existencias.asc())
                        .limit(5)
                        .queryList();

        if (productoList.size() >= 5) {
            List<SliceValue> values = new ArrayList<>();
            for (int i = 0; i < productoList.size(); ++i) {
                SliceValue sliceValue = new SliceValue(productoList.get(i).getExistencias(), ChartUtils.nextColor());
                values.add(sliceValue);
            }

            data = new PieChartData(values);
            data.setHasLabels(true);
            pieChart.setValueSelectionEnabled(true);
            pieChart.setPieChartData(data);

            txtProducto1.setText(productoList.get(0).getNombreProducto());
            txtProducto2.setText(productoList.get(1).getNombreProducto());
            txtProducto3.setText(productoList.get(2).getNombreProducto());
            txtProducto4.setText(productoList.get(3).getNombreProducto());
            txtProducto5.setText(productoList.get(4).getNombreProducto());
        } else {
            Toast.makeText(this,
                    "Debe tener al menos 5 productos registrados para generar estadisticas de productos",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
