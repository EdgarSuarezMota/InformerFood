package org.edgarsuarezmota.informer.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FoodAdapter foodAdapter;
    List<FoodEntity> foodList;
    String barcode = null;
    SharedPreferencesManager sharedPreferencesManager;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    barcode = result.getContents();
                    makeApiCall(barcode);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodList = new ArrayList<>();

        // Inicializar SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);
        List<FoodEntity> savedFoodList = sharedPreferencesManager.getFoodList();
        if (savedFoodList != null && !savedFoodList.isEmpty()) {
            // Recuperar la lista de alimentos guardada
            foodList.addAll(savedFoodList);
        }

        // Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador y asignarlo al RecyclerView
        foodAdapter = new FoodAdapter(foodList, this);
        recyclerView.setAdapter(foodAdapter);

        // Agregar el listener al adaptador
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, FoodEntity foodEntity) {
                // Handle item click here
                // Crear un Intent para abrir la actividad DetailsFood (reemplaza 'DetailsFood' con el nombre correcto de tu actividad)
                Intent intent = new Intent(getApplicationContext(), DetailsFood.class);

                // Puedes pasar datos adicionales a la nueva actividad, por ejemplo, el ID del artículo
                intent.putExtra("foodName", foodEntity.getName());
                intent.putExtra("foodCode", foodEntity.getCode());
                if (foodEntity.getUrl() == null || foodEntity.getUrl().isEmpty()) {
                    intent.putExtra("foodUrl", "h");
                } else {
                    intent.putExtra("foodUrl", foodEntity.getUrl());
                }
                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });

        FloatingActionButton add = findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeLauncher.launch(new ScanOptions());
            }
        });
    }

    // Método de ejemplo para crear una lista de FoodEntity de prueba
    private void makeApiCall(String barcode) {
        // Verificar si el código de barras es nulo antes de hacer la llamada a la API
        if (barcode == null) {
            // Manejar el caso en el que el código de barras es nulo (por ejemplo, mostrar un mensaje o retornar)
            Toast.makeText(this, "El código de barras es nulo", Toast.LENGTH_LONG).show();
            return;
        } else {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://world.openfoodfacts.org/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);

            Call<OpenFoodResponse> call = service.getProductDetails(barcode);
            call.enqueue(new Callback<OpenFoodResponse>() {
                @Override
                public void onResponse(Call<OpenFoodResponse> call, Response<OpenFoodResponse> response) {
                    if (response.isSuccessful()) {
                        String code = response.body().getCode(); // Obtener el código directamente
                        OpenFoodResponse.ProductInfo productInfo = response.body().getProductInfo();
                        if (productInfo != null) {
                            String genericName = productInfo.getProductName();
                            String imageUrl = productInfo.getImageUrl();

                            // Crear una instancia de FoodEntity con los datos obtenidos
                            FoodEntity foodEntity = new FoodEntity(genericName, code, imageUrl);

                            // Agregar la FoodEntity a la lista de alimentos y notificar al adaptador
                            foodList.add(foodEntity);
                            foodAdapter.setFoodList(foodList);

                            // Guardar la lista de alimentos actualizada en SharedPreferences
                            sharedPreferencesManager.saveFoodList(foodList);
                        }
                    } else {
                        // Manejar la respuesta no exitosa
                        Log.e("OpenFood", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<OpenFoodResponse> call, Throwable t) {
                    // Manejar el fallo de la llamada
                    Log.e("OpenFood", "Error: " + t.getMessage());
                }
            });
        }
    }
}
