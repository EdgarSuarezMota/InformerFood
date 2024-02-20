package org.edgarsuarezmota.informer.food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.edgarsuarezmota.informer.food.NuntrientEntity;

import java.util.List;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.NutrientViewHolder> {

    private List<NuntrientEntity> nutrientList;

    public NutrientAdapter(List<NuntrientEntity> nutrientList) {
        this.nutrientList = nutrientList;
    }

    @NonNull
    @Override
    public NutrientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_nutrients, parent, false);
        return new NutrientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientViewHolder holder, int position) {
        NuntrientEntity nuntrientEntity = nutrientList.get(position);
        holder.bind(nuntrientEntity);
    }

    @Override
    public int getItemCount() {
        return nutrientList.size();
    }

    // Método para establecer la lista de nutrientes
    public void setNutrientList(List<NuntrientEntity> nutrientList) {
        this.nutrientList = nutrientList;
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    public static class NutrientViewHolder extends RecyclerView.ViewHolder {

        private final TextView labelTextView;
        private final TextView valueTextView;

        public NutrientViewHolder(View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.tv_name_label_nutrient); // Asegúrate de tener un TextView con este ID en tu item_nutrient.xml
            valueTextView = itemView.findViewById(R.id.tv_value_nutrient); // Asegúrate de tener un TextView con este ID en tu item_nutrient.xml
        }

        public void bind(NuntrientEntity nutrientEntity) {
            labelTextView.setText(nutrientEntity.getLabel());
            valueTextView.setText(nutrientEntity.getValue() + nutrientEntity.getUnit());
        }
    }
}

