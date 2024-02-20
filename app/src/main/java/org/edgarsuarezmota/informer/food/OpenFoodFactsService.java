package org.edgarsuarezmota.informer.food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodFactsService {

    // Método para obtener detalles del producto
    @GET("product/{barcode}.json")
    Call<OpenFoodResponse> getProductDetails(@Path("barcode") String barcode);

    // Nuevo método para obtener detalles de nutrientes
    @GET("product/{barcode}.json")
    Call<OpenFoodResponseNutrients> getProductDetailsNutrients(@Path("barcode") String barcode);
}

