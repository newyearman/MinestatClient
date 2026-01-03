package com.minestat.client.cosmetics;

/**
 * Types of cosmetic items
 */
public enum CosmeticType {
    CAPE("Cape"),
    WINGS("Wings"),
    HAT("Hat"),
    PARTICLE("Particle Effect"),
    EMOTE("Emote");
    
    private final String displayName;
    
    CosmeticType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
