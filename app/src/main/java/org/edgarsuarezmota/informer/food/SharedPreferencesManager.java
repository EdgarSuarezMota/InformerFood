package org.edgarsuarezmota.informer.food;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveFoodList(List<FoodEntity> foodList) {
        String json = gson.toJson(foodList);
        sharedPreferences.edit().putString("food_list", json).apply();
    }

    public List<FoodEntity> getFoodList() {
        String json = sharedPreferences.getString("food_list", null);
        Type type = new TypeToken<List<FoodEntity>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : null;
    }
}


