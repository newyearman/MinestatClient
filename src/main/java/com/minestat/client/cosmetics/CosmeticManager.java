package com.minestat.client.cosmetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Manages the cosmetics system including capes, wings, hats, particles, and emotes
 */
public class CosmeticManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CosmeticManager.class);
    
    private CosmeticInventory inventory;
    private CosmeticRenderer renderer;
    private CosmeticStore store;
    private Map<String, CosmeticProfile> profiles;
    
    private CosmeticProfile activeProfile;
    
    public void initialize() {
        LOGGER.info("Initializing cosmetics system...");
        
        inventory = new CosmeticInventory();
        renderer = new CosmeticRenderer();
        store = new CosmeticStore();
        profiles = new HashMap<>();
        
        // Load default cosmetics
        loadDefaultCosmetics();
        
        // Create default profile
        activeProfile = new CosmeticProfile("default");
    }
    
    private void loadDefaultCosmetics() {
        // Add default cosmetics to inventory
        inventory.addCosmetic(new Cosmetic("none", "None", CosmeticType.CAPE, 0));
        inventory.addCosmetic(new Cosmetic("default_cape", "Default Cape", CosmeticType.CAPE, 0));
        inventory.addCosmetic(new Cosmetic("red_cape", "Red Cape", CosmeticType.CAPE, 100));
        
        inventory.addCosmetic(new Cosmetic("none", "None", CosmeticType.WINGS, 0));
        inventory.addCosmetic(new Cosmetic("angel_wings", "Angel Wings", CosmeticType.WINGS, 500));
        inventory.addCosmetic(new Cosmetic("demon_wings", "Demon Wings", CosmeticType.WINGS, 500));
        
        inventory.addCosmetic(new Cosmetic("none", "None", CosmeticType.HAT, 0));
        inventory.addCosmetic(new Cosmetic("top_hat", "Top Hat", CosmeticType.HAT, 200));
        inventory.addCosmetic(new Cosmetic("crown", "Crown", CosmeticType.HAT, 300));
        
        inventory.addCosmetic(new Cosmetic("none", "None", CosmeticType.PARTICLE, 0));
        inventory.addCosmetic(new Cosmetic("hearts", "Hearts", CosmeticType.PARTICLE, 150));
        inventory.addCosmetic(new Cosmetic("flames", "Flames", CosmeticType.PARTICLE, 200));
        
        inventory.addCosmetic(new Cosmetic("none", "None", CosmeticType.EMOTE, 0));
        inventory.addCosmetic(new Cosmetic("wave", "Wave", CosmeticType.EMOTE, 50));
        inventory.addCosmetic(new Cosmetic("dance", "Dance", CosmeticType.EMOTE, 100));
    }
    
    /**
     * Equip a cosmetic item
     */
    public void equipCosmetic(String cosmeticId, CosmeticType type) {
        Cosmetic cosmetic = inventory.getCosmetic(cosmeticId);
        
        if (cosmetic != null && cosmetic.getType() == type) {
            activeProfile.equipCosmetic(type, cosmetic);
            LOGGER.info("Equipped {} of type {}", cosmeticId, type);
        } else {
            LOGGER.warn("Cannot equip cosmetic: {} (type: {})", cosmeticId, type);
        }
    }
    
    /**
     * Unequip a cosmetic type
     */
    public void unequipCosmetic(CosmeticType type) {
        activeProfile.unequipCosmetic(type);
        LOGGER.info("Unequipped cosmetic of type {}", type);
    }
    
    /**
     * Play an emote
     */
    public void playEmote(String emoteId) {
        Cosmetic emote = inventory.getCosmetic(emoteId);
        
        if (emote != null && emote.getType() == CosmeticType.EMOTE) {
            LOGGER.info("Playing emote: {}", emoteId);
            // Trigger emote animation
        }
    }
    
    /**
     * Save current cosmetics as a profile
     */
    public void saveProfile(String name) {
        CosmeticProfile profile = new CosmeticProfile(name);
        profile.copyFrom(activeProfile);
        profiles.put(name, profile);
        LOGGER.info("Saved cosmetic profile: {}", name);
    }
    
    /**
     * Load a cosmetic profile
     */
    public void loadProfile(String name) {
        CosmeticProfile profile = profiles.get(name);
        
        if (profile != null) {
            activeProfile.copyFrom(profile);
            LOGGER.info("Loaded cosmetic profile: {}", name);
        }
    }
    
    /**
     * Preview a cosmetic without equipping
     */
    public void previewCosmetic(String cosmeticId) {
        Cosmetic cosmetic = inventory.getCosmetic(cosmeticId);
        
        if (cosmetic != null) {
            LOGGER.info("Previewing cosmetic: {}", cosmeticId);
            // Show preview in UI
        }
    }
    
    public CosmeticInventory getInventory() {
        return inventory;
    }
    
    public CosmeticRenderer getRenderer() {
        return renderer;
    }
    
    public CosmeticStore getStore() {
        return store;
    }
    
    public CosmeticProfile getActiveProfile() {
        return activeProfile;
    }
    
    public void shutdown() {
        LOGGER.info("Shutting down cosmetics system");
        if (renderer != null) {
            renderer.cleanup();
        }
    }
}
