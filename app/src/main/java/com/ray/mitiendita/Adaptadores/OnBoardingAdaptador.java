package com.ray.mitiendita.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ray.mitiendita.OnBoardingItems;
import com.ray.mitiendita.R;

import java.util.List;

public class OnBoardingAdaptador
                extends RecyclerView.Adapter<OnBoardingAdaptador.OnBoardingViewHolder>{

    private List<OnBoardingItems> onBoardingItems;

    public OnBoardingAdaptador(List<OnBoardingItems> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.items_onboarding, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.setOnboardingDatos(onBoardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitulo;
        private TextView textDescripcion;
        private ImageView imgOnboarding;

        public OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            imgOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnboardingDatos(OnBoardingItems onBoardingItems){
            textTitulo.setText(onBoardingItems.getTitulo());
            textDescripcion.setText(onBoardingItems.getDescripcion());
            imgOnboarding.setImageResource(onBoardingItems.getImagen());
        }
    }
}
