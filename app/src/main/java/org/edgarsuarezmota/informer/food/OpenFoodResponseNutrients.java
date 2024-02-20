package org.edgarsuarezmota.informer.food;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class OpenFoodResponseNutrients {

    @SerializedName("product")
    private ProductInfo productInfo;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public static class ProductInfo {

        @SerializedName("categories")
        private String categories;

        @SerializedName("ingredients_text")
        private String ingredientsText;

        @SerializedName("nutriments")
        private Nutriments nutriments;

        @SerializedName("labels")
        private String labels;

        public String getCategories() {
            return categories;
        }

        public String getIngredientsText() {
            return ingredientsText;
        }

        public Nutriments getNutriments() {
            return nutriments;
        }

        public String getLabels() {
            return labels;
        }
    }

    public static class Nutriments {

        @SerializedName("calcium")
        private String calcium;

        @SerializedName("carbohydrates")
        private String carbohydrates;

        @SerializedName("cholesterol")
        private String cholesterol;

        @SerializedName("energy-kcal")
        private String energy;

        @SerializedName("fat")
        private String fat;

        @SerializedName("fiber")
        private String fiber;

        @SerializedName("iron")
        private String iron;

        @SerializedName("proteins")
        private String proteins;

        @SerializedName("salt")
        private String salt;

        @SerializedName("saturated-fat")
        private String saturatedFat;

        @SerializedName("sodium")
        private String sodium;

        @SerializedName("sugars")
        private String sugars;

        public String getCalcium() {
            return calcium;
        }

        public String getCarbohydrates() {
            return carbohydrates;
        }

        public String getCholesterol() {
            return cholesterol;
        }

        public String getEnergy() {
            return energy;
        }

        public String getFat() {
            return fat;
        }

        public String getFiber() {
            return fiber;
        }

        public String getIron() {
            return iron;
        }

        public String getProteins() {
            return proteins;
        }

        public String getSalt() {
            return salt;
        }

        public String getSaturatedFat() {
            return saturatedFat;
        }

        public String getSodium() {
            return sodium;
        }

        public String getSugars() {
            return sugars;
        }
    }



}
