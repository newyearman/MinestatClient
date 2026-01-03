package com.minestat.client.cosmetics;

import java.util.*;

/**
 * Manages user's cosmetic inventory
 */
public class CosmeticInventory {
    
    private final Map<String, Cosmetic> cosmetics;
    private final Set<String> ownedCosmetics;
    
    public CosmeticInventory() {
        this.cosmetics = new HashMap<>();
        this.ownedCosmetics = new HashSet<>();
    }
    
    /**
     * Add a cosmetic to the available cosmetics
     */
    public void addCosmetic(Cosmetic cosmetic) {
        cosmetics.put(cosmetic.getId(), cosmetic);
        
        // Free cosmetics are automatically owned
        if (cosmetic.getPrice() == 0) {
            ownedCosmetics.add(cosmetic.getId());
        }
    }
    
    /**
     * Purchase a cosmetic
     */
    public boolean purchaseCosmetic(String cosmeticId, int userCoins) {
        Cosmetic cosmetic = cosmetics.get(cosmeticId);
        
        if (cosmetic == null) {
            return false;
        }
        
        if (ownedCosmetics.contains(cosmeticId)) {
            return false; // Already owned
        }
        
        if (userCoins >= cosmetic.getPrice()) {
            ownedCosmetics.add(cosmeticId);
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if user owns a cosmetic
     */
    public boolean ownsCosmetic(String cosmeticId) {
        return ownedCosmetics.contains(cosmeticId);
    }
    
    /**
     * Get a cosmetic by ID
     */
    public Cosmetic getCosmetic(String id) {
        return cosmetics.get(id);
    }
    
    /**
     * Get all cosmetics of a specific type
     */
    public List<Cosmetic> getCosmeticsByType(CosmeticType type) {
        List<Cosmetic> result = new ArrayList<>();
        
        for (Cosmetic cosmetic : cosmetics.values()) {
            if (cosmetic.getType() == type) {
                result.add(cosmetic);
            }
        }
        
        return result;
    }
    
    /**
     * Get all owned cosmetics
     */
    public List<Cosmetic> getOwnedCosmetics() {
        List<Cosmetic> result = new ArrayList<>();
        
        for (String id : ownedCosmetics) {
            Cosmetic cosmetic = cosmetics.get(id);
            if (cosmetic != null) {
                result.add(cosmetic);
            }
        }
        
        return result;
    }
}
