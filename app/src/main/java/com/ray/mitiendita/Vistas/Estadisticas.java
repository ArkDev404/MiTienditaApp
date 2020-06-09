package com.ray.mitiendita.Vistas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        int numValues = 5;

        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue(5, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(true);
        pieChart.setValueSelectionEnabled(true);
        pieChart.setCircleFillRatio(0.85f);
        pieChart.setPieChartData(data);

    }
}
