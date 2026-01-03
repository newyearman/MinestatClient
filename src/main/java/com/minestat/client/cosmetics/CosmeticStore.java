package com.minestat.client.cosmetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Manages the cosmetic store/market
 */
public class CosmeticStore {
    
    private static final Logger LOGGER = LogManager.getLogger(CosmeticStore.class);
    
    private final List<Cosmetic> featuredCosmetics;
    private final Map<String, Integer> prices;
    
    public CosmeticStore() {
        this.featuredCosmetics = new ArrayList<>();
        this.prices = new HashMap<>();
        
        loadFeaturedCosmetics();
    }
    
    private void loadFeaturedCosmetics() {
        // Load featured cosmetics from server or config
        LOGGER.info("Loading featured cosmetics");
    }
    
    /**
     * Get featured cosmetics for display
     */
    public List<Cosmetic> getFeaturedCosmetics() {
        return new ArrayList<>(featuredCosmetics);
    }
    
    /**
     * Get price of a cosmetic
     */
    public int getPrice(String cosmeticId) {
        return prices.getOrDefault(cosmeticId, 0);
    }
    
    /**
     * Purchase a cosmetic
     */
    public boolean purchase(String cosmeticId, int userBalance) {
        int price = getPrice(cosmeticId);
        
        if (userBalance >= price) {
            LOGGER.info("Purchased cosmetic: {} for {} coins", cosmeticId, price);
            return true;
        }
        
        LOGGER.warn("Insufficient funds to purchase: {}", cosmeticId);
        return false;
    }
}
