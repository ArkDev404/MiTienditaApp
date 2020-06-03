package com.ray.mitiendita.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.ray.mitiendita.Adaptadores.OnBoardingAdaptador;
import com.ray.mitiendita.OnBoardingItems;
import com.ray.mitiendita.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingScreen extends AppCompatActivity {

    @BindView(R.id.onBoardingView)
    ViewPager2 onBoardingView;
    @BindView(R.id.layoutIndicators)
    LinearLayout layoutIndicators;
    @BindView(R.id.botonBoarding)
    MaterialButton botonBoarding;
    private OnBoardingAdaptador onBoardingAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);
        ButterKnife.bind(this);

        setupOnBoardingItems();

        onBoardingView.setAdapter(onBoardingAdaptador);

        setupOnBoardingIndicators();

        setCurrentOnBoardingIndicator(0);

        onBoardingView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoardingIndicator(position);
            }
        });

    }

    private void setupOnBoardingItems() {

        List<OnBoardingItems> onBoardingItems = new ArrayList<>();

        OnBoardingItems itemVentas = new OnBoardingItems();
        itemVentas.setTitulo("Verifica tus Ventas");
        itemVentas.setDescripcion("Controla todas tus ventas desde la aplicación y has un seguimiento de tus ingresos");
        itemVentas.setImagen(R.drawable.rebaja);

        OnBoardingItems itemControlCuentas = new OnBoardingItems();
        itemControlCuentas.setTitulo("Controla tus Clientes");
        itemControlCuentas.setDescripcion("Ahora es mucho mas facil saber quienes son los clientes que le deben efectivo");
        itemControlCuentas.setImagen(R.drawable.dinero);

        OnBoardingItems itemInventario = new OnBoardingItems();
        itemInventario.setTitulo("Asegura tu Inventario");
        itemInventario.setDescripcion("Ademas puedes verificar con cuantos productos cuentas y asegurar existencias en tu negocio");
        itemInventario.setImagen(R.drawable.proveedor);

        onBoardingItems.add(itemVentas);
        onBoardingItems.add(itemControlCuentas);
        onBoardingItems.add(itemInventario);

        onBoardingAdaptador = new OnBoardingAdaptador(onBoardingItems);
    }

    private void setupOnBoardingIndicators() {
        ImageView[] indicator = new ImageView[onBoardingAdaptador.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicadores_inactivo
            ));
            indicator[i].setLayoutParams(layoutParams);
            layoutIndicators.addView(indicator[i]);
        }
    }

    private void setCurrentOnBoardingIndicator(int index) {
        int childCount = layoutIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicadores_activo)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicadores_inactivo)
                );
            }
            if ( index == onBoardingAdaptador.getItemCount() - 1){
                botonBoarding.setText("Iniciar");
            } else {
                botonBoarding.setText("Siguiente");
            }
        }
    }

    @OnClick(R.id.botonBoarding)
    public void onViewClicked() {
        if (onBoardingView.getCurrentItem() + 1 < onBoardingAdaptador.getItemCount()){
            onBoardingView.setCurrentItem(onBoardingView.getCurrentItem() + 1);
        } else {
            startActivity( new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
    }
}
