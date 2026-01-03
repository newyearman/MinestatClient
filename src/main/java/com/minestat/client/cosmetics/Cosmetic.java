package com.minestat.client.cosmetics;

/**
 * Represents a cosmetic item
 */
public class Cosmetic {
    
    private final String id;
    private final String name;
    private final CosmeticType type;
    private final int price;
    private final String description;
    private final boolean animated;
    
    public Cosmetic(String id, String name, CosmeticType type, int price) {
        this(id, name, type, price, "", false);
    }
    
    public Cosmetic(String id, String name, CosmeticType type, int price, String description, boolean animated) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.animated = animated;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public CosmeticType getType() {
        return type;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isAnimated() {
        return animated;
    }
}
