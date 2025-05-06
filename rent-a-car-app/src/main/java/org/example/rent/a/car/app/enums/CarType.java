package org.example.rent.a.car.app.enums;

public enum CarType {
    ECONOMY("0", 18),
    STANDARD("1", 30),
    SUV("2", 33);

    private String code;
    private final int priceRange;

    CarType(String code, int priceRange) {
        this.code = code;
        this.priceRange = priceRange;
    }

    public int getPriceRange() {
        return this.priceRange;
    }

    public String getCode() {
        return this.code;
    }

    public static String getEnumByString(String code) {
        for (CarType carType : CarType.values()) {
            if (carType.getCode().equals(code)) return carType.name();
        }
        return null;
    }
}
