package org.edgarsuarezmota.informer.food;

public class NuntrientEntity {
    private String label;
    private String value;
    private String unit;

    public NuntrientEntity(String label, String value, String unit) {
        this.label = label;
        this.value = value;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "NuntrientEntity{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

