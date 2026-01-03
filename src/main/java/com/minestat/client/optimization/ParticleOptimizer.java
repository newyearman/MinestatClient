package com.minestat.client.optimization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Optimizes particle rendering and density
 */
public class ParticleOptimizer {
    
    private static final Logger LOGGER = LogManager.getLogger(ParticleOptimizer.class);
    
    private boolean enabled = false;
    private int maxParticles = 4000;
    private int currentParticleCount = 0;
    
    public void enable() {
        enabled = true;
        LOGGER.info("Particle optimization enabled (max particles: {})", maxParticles);
    }
    
    public void disable() {
        enabled = false;
        LOGGER.info("Particle optimization disabled");
    }
    
    /**
     * Check if a new particle can be spawned
     */
    public boolean canSpawnParticle() {
        if (!enabled) {
            return true;
        }
        
        return currentParticleCount < maxParticles;
    }
    
    /**
     * Register a new particle
     */
    public void registerParticle() {
        if (enabled) {
            currentParticleCount++;
        }
    }
    
    /**
     * Unregister a particle (when it expires)
     */
    public void unregisterParticle() {
        if (enabled && currentParticleCount > 0) {
            currentParticleCount--;
        }
    }
    
    /**
     * Get particle cull distance based on settings
     */
    public double getParticleCullDistance() {
        if (!enabled) {
            return Double.MAX_VALUE;
        }
        
        return 32.0; // Cull particles beyond 32 blocks
    }
    
    /**
     * Check if particle should be rendered based on distance
     */
    public boolean shouldRenderParticle(double distanceSq) {
        if (!enabled) {
            return true;
        }
        
        double cullDistance = getParticleCullDistance();
        return distanceSq <= cullDistance * cullDistance;
    }
    
    public void setMaxParticles(int max) {
        this.maxParticles = max;
        LOGGER.info("Max particles set to: {}", max);
    }
    
    public int getCurrentParticleCount() {
        return currentParticleCount;
    }
}
