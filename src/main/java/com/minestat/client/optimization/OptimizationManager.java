package com.minestat.client.optimization;

import com.minestat.client.MinestatClient;
import com.minestat.client.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages FPS optimization and performance features
 * OptiFine-like optimizations for better performance
 */
public class OptimizationManager {
    
    private static final Logger LOGGER = LogManager.getLogger(OptimizationManager.class);
    
    private DynamicFPSController dynamicFPS;
    private FastRenderOptimizer fastRender;
    private ChunkOptimizer chunkOptimizer;
    private EntityRenderOptimizer entityOptimizer;
    private ParticleOptimizer particleOptimizer;
    
    private ConfigManager.PerformanceSettings settings;
    
    public void initialize() {
        LOGGER.info("Initializing optimization system...");
        
        settings = MinestatClient.getInstance().getConfigManager().getConfig().performance;
        
        // Initialize optimization modules
        if (settings.dynamicFps) {
            dynamicFPS = new DynamicFPSController();
            dynamicFPS.enable();
        }
        
        if (settings.fastRender) {
            fastRender = new FastRenderOptimizer();
            fastRender.enable();
        }
        
        if (settings.chunkOptimization) {
            chunkOptimizer = new ChunkOptimizer();
            chunkOptimizer.enable();
        }
        
        entityOptimizer = new EntityRenderOptimizer(settings.entityRenderDistance);
        entityOptimizer.enable();
        
        if (settings.particleOptimization) {
            particleOptimizer = new ParticleOptimizer();
            particleOptimizer.enable();
        }
        
        LOGGER.info("Optimization system initialized");
    }
    
    /**
     * Update optimization systems each frame
     */
    public void update() {
        if (dynamicFPS != null) {
            dynamicFPS.update();
        }
        
        if (fastRender != null) {
            fastRender.update();
        }
    }
    
    /**
     * Set entity render distance
     */
    public void setEntityRenderDistance(int distance) {
        if (entityOptimizer != null) {
            entityOptimizer.setRenderDistance(distance);
            settings.entityRenderDistance = distance;
        }
    }
    
    /**
     * Get current FPS
     */
    public int getCurrentFPS() {
        if (dynamicFPS != null) {
            return dynamicFPS.getCurrentFPS();
        }
        return 0;
    }
    
    /**
     * Check if window is focused
     */
    public boolean isWindowFocused() {
        // In real implementation, this would check actual window focus
        return true;
    }
    
    public void shutdown() {
        LOGGER.info("Shutting down optimization system");
        
        if (dynamicFPS != null) {
            dynamicFPS.disable();
        }
        
        if (fastRender != null) {
            fastRender.disable();
        }
        
        if (chunkOptimizer != null) {
            chunkOptimizer.disable();
        }
        
        if (entityOptimizer != null) {
            entityOptimizer.disable();
        }
        
        if (particleOptimizer != null) {
            particleOptimizer.disable();
        }
    }
}
