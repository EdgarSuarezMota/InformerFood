package org.edgarsuarezmota.informer.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsFood extends AppCompatActivity {
    private NutrientAdapter nutrientAdapter;
    private OpenFoodResponseNutrients.ProductInfo productInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_food);
        showProgressIndicator();
        RecyclerView recyclerView = findViewById(R.id.rv_details_food_nutrients_nutrients);
        nutrientAdapter = new NutrientAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nutrientAdapter);
        Intent intent = getIntent();
        MaterialToolbar toolbar = findViewById(R.id.details_food_top_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String foodName = intent.getStringExtra("foodName");
        String foodCode = intent.getStringExtra("foodCode");
        String foodUrl = intent.getStringExtra("foodUrl");

        // Ahora puedes usar estos datos como desees, por ejemplo, mostrarlos en TextViews
        TextView nameTextView = findViewById(R.id.tv_details_food_product_name);
        TextView codeTextView = findViewById(R.id.tv_details_food_product_code);


        nameTextView.setText(foodName);
        codeTextView.setText(foodCode);

        if (!foodUrl.equals("h")) {
            // Carga la imagen utilizando Glide
            ImageView imageView = findViewById(R.id.iv_details_food_product_image);
            Glide.with(this).load(foodUrl).into(imageView);
        }

        // Obtén el código de barras del producto de alguna manera
        String barcode = foodCode;

        // Inicializa Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://world.openfoodfacts.org/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        // Crea una instancia de la interfaz del servicio
        OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);

        // Realiza la llamada a la API para obtener detalles del producto
        Call<OpenFoodResponseNutrients> call = service.getProductDetailsNutrients(barcode);
        call.enqueue(new Callback<OpenFoodResponseNutrients>() {
            @Override
            public void onResponse(Call<OpenFoodResponseNutrients> call, Response<OpenFoodResponseNutrients> response) {
                if (response.isSuccessful()) {
                    // Obtén la información del producto
                    productInfo = response.body().getProductInfo();

                    // Muestra la información en los TextViews y RecyclerView
                    showCategories(productInfo.getCategories());
                    showIngredients(productInfo.getIngredientsText());
                    showNutrients(productInfo.getNutriments());
                    showLabels(productInfo.getLabels());
                    hideProgressIndicator();
                    // Obtener el número de elementos en el adaptador y mostrar en el Log
                    int itemCount = nutrientAdapter.getItemCount();
                    Log.d("AdapterItemCount", "Número de elementos en el adaptador: " + itemCount);
                } else {
                    // Manejar la respuesta no exitosa
                    // Puedes mostrar un mensaje de error o realizar otra acción
                    Log.i("hhhh","Jjjjj");
                }
            }

            @Override
            public void onFailure(Call<OpenFoodResponseNutrients> call, Throwable t) {
                // Manejar el fallo de la llamada
                // Puedes mostrar un mensaje de error o realizar otra acción
            }
        });
    }

    private void showCategories(String categories) {
        TextView categoriesTextView = findViewById(R.id.tv_details_food_categories_categories);
        if (categories == null || categories.isEmpty()) {
            categoriesTextView.setText("Categories not available");
        } else {
            categoriesTextView.setText(categories);
        }
    }

    private void showIngredients(String ingredients) {
        TextView ingredientsTextView = findViewById(R.id.tv_details_food_ingredients_ingredients);
        if (ingredients == null || ingredients.isEmpty() ) {
            ingredientsTextView.setText("Ingredients not available");
        } else {
            ingredientsTextView.setText(ingredients);
        }

    }

    private void showLabels(String labels) {
        TextView labelsTextView = findViewById(R.id.tv_details_food_labels_labels);
        if (labels == null || labels.isEmpty()) {
            labelsTextView.setText("Labels not available");
        } else {
            labelsTextView.setText(labels);
        }
    }

    // Dentro de la clase DetailsFood
    private void showNutrients(OpenFoodResponseNutrients.Nutriments nutriments) {
        // Aquí debes obtener los datos de nutrientes y agregarlos al adaptador
        List<NuntrientEntity> nutrientList = createNutrientData(nutriments);
        // Agregar los datos al adaptador
        nutrientAdapter.setNutrientList(nutrientList);
    }

    private List<NuntrientEntity> createNutrientData(OpenFoodResponseNutrients.Nutriments nutriments) {
        // Aquí debes extraer los datos reales de nutrientes de nutriments y crear una lista de NutrientEntity
        List<NuntrientEntity> nutrientList = new ArrayList<>();
        String calcium = nutriments.getCalcium();
        if (calcium != null && !calcium.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Calcium", calcium, " mg"));
        }
        String carbohydrates = nutriments.getCarbohydrates();
        if (carbohydrates != null && !carbohydrates.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Carbohydrates", carbohydrates, " g"));
        }
        String cholesterol = nutriments.getCholesterol();
        if (cholesterol != null && !cholesterol.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Cholesterol", cholesterol, " mg"));
        }
        String energy = nutriments.getEnergy();
        if (energy != null && !energy.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Energy", energy, " kcal"));
        }
        String fat = nutriments.getFat();
        if (fat != null && !fat.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Fat", fat, " g"));
        }
        String fiber = nutriments.getFiber();
        if (fiber != null && !fiber.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Fiber", fiber, " g"));
        }
        String iron = nutriments.getIron();
        if (iron != null && !iron.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Iron", iron, " mg"));
        }
        String proteins = nutriments.getProteins();
        if (proteins != null && !proteins.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Proteins", proteins, " g"));
        }
        String salt = nutriments.getSalt();
        if (salt != null && !salt.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Salt", salt, " mg"));
        }
        String saturated_fat = nutriments.getSaturatedFat();
        if (saturated_fat != null && !saturated_fat.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Saturated Fat", saturated_fat, " g"));
        }
        String sodium = nutriments.getSodium();
        if (sodium != null && !sodium.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Sodium", sodium, " mg"));
        }
        String sugars = nutriments.getSugars();
        if (sugars != null && !sugars.isEmpty()) {
            nutrientList.add(new NuntrientEntity("Sugars", sugars, " g"));
        }

        return nutrientList;
    }

    private void showProgressIndicator() {
        CircularProgressIndicator progressIndicator = findViewById(R.id.cpi_api);
        TextView t = findViewById(R.id.tv_details_food_categories_title);
        TextView tt = findViewById(R.id.tv_details_food_categories_categories);
        MaterialDivider d = findViewById(R.id.d_2);
        TextView ttt = findViewById(R.id.tv_details_food_ingredients_title);
        TextView tttt = findViewById(R.id.tv_details_food_ingredients_ingredients);
        MaterialDivider dd = findViewById(R.id.d_3);
        TextView ttttt = findViewById(R.id.tv_details_food_nutrients_title);
        RecyclerView r = findViewById(R.id.rv_details_food_nutrients_nutrients);
        MaterialDivider ddd = findViewById(R.id.d_4);
        TextView tttttt = findViewById(R.id.tv_details_food_labels_title);
        TextView ttttttt = findViewById(R.id.tv_details_food_labels_labels);
        progressIndicator.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
        tt.setVisibility(View.GONE);
        ttt.setVisibility(View.GONE);
        tttt.setVisibility(View.GONE);
        d.setVisibility(View.GONE);
        dd.setVisibility(View.GONE);
        ddd.setVisibility(View.GONE);
        ttttt.setVisibility(View.GONE);
        tttttt.setVisibility(View.GONE);
        ttttttt.setVisibility(View.GONE);
        r.setVisibility(View.GONE);
    }

    private void hideProgressIndicator() {
        CircularProgressIndicator progressIndicator = findViewById(R.id.cpi_api);
        TextView t = findViewById(R.id.tv_details_food_categories_title);
        TextView tt = findViewById(R.id.tv_details_food_categories_categories);
        MaterialDivider d = findViewById(R.id.d_2);
        TextView ttt = findViewById(R.id.tv_details_food_ingredients_title);
        TextView tttt = findViewById(R.id.tv_details_food_ingredients_ingredients);
        MaterialDivider dd = findViewById(R.id.d_3);
        TextView ttttt = findViewById(R.id.tv_details_food_nutrients_title);
        RecyclerView r = findViewById(R.id.rv_details_food_nutrients_nutrients);
        MaterialDivider ddd = findViewById(R.id.d_4);
        TextView tttttt = findViewById(R.id.tv_details_food_labels_title);
        TextView ttttttt = findViewById(R.id.tv_details_food_labels_labels);
        t.setVisibility(View.VISIBLE);
        tt.setVisibility(View.VISIBLE);
        ttt.setVisibility(View.VISIBLE);
        tttt.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        dd.setVisibility(View.VISIBLE);
        ddd.setVisibility(View.VISIBLE);
        ttttt.setVisibility(View.VISIBLE);
        tttttt.setVisibility(View.VISIBLE);
        ttttttt.setVisibility(View.VISIBLE);
        r.setVisibility(View.VISIBLE);
        progressIndicator.setVisibility(View.GONE);
    }
}
