package com.minestat.client.cosmetics;

import java.util.*;

/**
 * Represents a cosmetic profile with equipped items
 */
public class CosmeticProfile {
    
    private final String name;
    private final Map<CosmeticType, Cosmetic> equippedCosmetics;
    
    public CosmeticProfile(String name) {
        this.name = name;
        this.equippedCosmetics = new EnumMap<>(CosmeticType.class);
    }
    
    /**
     * Equip a cosmetic
     */
    public void equipCosmetic(CosmeticType type, Cosmetic cosmetic) {
        equippedCosmetics.put(type, cosmetic);
    }
    
    /**
     * Unequip a cosmetic type
     */
    public void unequipCosmetic(CosmeticType type) {
        equippedCosmetics.remove(type);
    }
    
    /**
     * Get equipped cosmetic of a type
     */
    public Cosmetic getEquipped(CosmeticType type) {
        return equippedCosmetics.get(type);
    }
    
    /**
     * Check if a type is equipped
     */
    public boolean hasEquipped(CosmeticType type) {
        return equippedCosmetics.containsKey(type);
    }
    
    /**
     * Copy from another profile
     */
    public void copyFrom(CosmeticProfile other) {
        equippedCosmetics.clear();
        equippedCosmetics.putAll(other.equippedCosmetics);
    }
    
    /**
     * Get all equipped cosmetics
     */
    public Map<CosmeticType, Cosmetic> getAllEquipped() {
        return new EnumMap<>(equippedCosmetics);
    }
    
    public String getName() {
        return name;
    }
}
