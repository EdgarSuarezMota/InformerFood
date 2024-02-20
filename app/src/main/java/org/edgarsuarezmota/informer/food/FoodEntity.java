package org.edgarsuarezmota.informer.food;

public class FoodEntity {
    private String name;
    private String code;
    private String url;

    public FoodEntity(String name, String code, String url) {
        this.name = name;
        this.code = code;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    // Otros métodos generados automáticamente si son necesarios.

    @Override
    public String toString() {
        return "FoodEntity{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
