package org.edgarsuarezmota.informer.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.edgarsuarezmota.informer.food.FoodEntity;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodEntity> foodList;
    private Context context; // Necesario para Glide
    private OnItemClickListener onItemClickListener; // Interfaz para gestionar clics

    public FoodAdapter(List<FoodEntity> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodEntity foodEntity = foodList.get(position);
        holder.bind(foodEntity);

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), foodEntity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView codeTextView;
        private final ImageView imageView; // Nuevo ImageView para mostrar la imagen

        public FoodViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name_food);
            codeTextView = itemView.findViewById(R.id.tv_code_food);
            imageView = itemView.findViewById(R.id.iv_food); // Asegúrate de tener un ImageView con este ID en tu item_food.xml
        }

        public void bind(FoodEntity foodEntity) {
            nameTextView.setText(foodEntity.getName());
            codeTextView.setText(foodEntity.getCode());

            if (foodEntity.getUrl() != null && !foodEntity.getUrl().isEmpty()) {
                // Cargar la imagen con Glide
                Glide.with(context)
                        .load(foodEntity.getUrl())
                        .into(imageView);
            }

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, FoodEntity foodEntity);
    }

    public void setFoodList(List<FoodEntity> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged(); // Este método notificará al RecyclerView sobre el cambio en los datos
    }
}
