package org.edgarsuarezmota.informer.food;

import com.google.gson.annotations.SerializedName;

public class OpenFoodResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("product")
    private ProductInfo productInfo;

    public String getCode() {
        return code;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public static class ProductInfo {

        @SerializedName("product_name")
        private String productName;


        @SerializedName("image_url")
        private String imageUrl;

        public String getProductName() {
            return productName;
        }


        public String getImageUrl() {
            return imageUrl;
        }
    }
}



