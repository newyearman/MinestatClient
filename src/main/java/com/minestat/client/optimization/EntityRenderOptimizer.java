package com.minestat.client.optimization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Optimizes entity rendering based on distance
 */
public class EntityRenderOptimizer {
    
    private static final Logger LOGGER = LogManager.getLogger(EntityRenderOptimizer.class);
    
    private boolean enabled = false;
    private int renderDistance;
    
    public EntityRenderOptimizer(int renderDistance) {
        this.renderDistance = renderDistance;
    }
    
    public void enable() {
        enabled = true;
        LOGGER.info("Entity render optimization enabled (distance: {} blocks)", renderDistance);
    }
    
    public void disable() {
        enabled = false;
        LOGGER.info("Entity render optimization disabled");
    }
    
    /**
     * Check if entity should be rendered based on distance
     */
    public boolean shouldRenderEntity(double entityX, double entityY, double entityZ,
                                     double playerX, double playerY, double playerZ) {
        if (!enabled) {
            return true;
        }
        
        double dx = entityX - playerX;
        double dy = entityY - playerY;
        double dz = entityZ - playerZ;
        
        double distanceSq = dx * dx + dy * dy + dz * dz;
        double maxDistanceSq = renderDistance * renderDistance;
        
        return distanceSq <= maxDistanceSq;
    }
    
    /**
     * Get level of detail for entity based on distance
     */
    public int getLODLevel(double distance) {
        if (!enabled) {
            return 0; // Full detail
        }
        
        if (distance < renderDistance * 0.25) {
            return 0; // High detail
        } else if (distance < renderDistance * 0.5) {
            return 1; // Medium detail
        } else if (distance < renderDistance * 0.75) {
            return 2; // Low detail
        } else {
            return 3; // Very low detail
        }
    }
    
    public void setRenderDistance(int distance) {
        this.renderDistance = distance;
        LOGGER.info("Entity render distance set to: {} blocks", distance);
    }
    
    public int getRenderDistance() {
        return renderDistance;
    }
}
