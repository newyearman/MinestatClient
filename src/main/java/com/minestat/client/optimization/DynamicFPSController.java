package com.minestat.client.optimization;

import com.minestat.client.MinestatClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controls dynamic FPS - reduces FPS when window is not focused
 */
public class DynamicFPSController {
    
    private static final Logger LOGGER = LogManager.getLogger(DynamicFPSController.class);
    
    private static final int BACKGROUND_FPS = 10;
    private static final int FOREGROUND_FPS = 120;
    
    private boolean enabled = false;
    private int currentTargetFPS = FOREGROUND_FPS;
    private int currentFPS = 0;
    
    private long lastFrameTime = System.nanoTime();
    private long frameTimeNanos;
    
    public void enable() {
        enabled = true;
        updateTargetFPS();
        LOGGER.info("Dynamic FPS enabled (Background: {} FPS, Foreground: {} FPS)", 
                    BACKGROUND_FPS, FOREGROUND_FPS);
    }
    
    public void disable() {
        enabled = false;
        LOGGER.info("Dynamic FPS disabled");
    }
    
    public void update() {
        if (!enabled) {
            return;
        }
        
        // Check if window focus changed
        boolean focused = MinestatClient.getInstance()
                                       .getOptimizationManager()
                                       .isWindowFocused();
        
        int targetFPS = focused ? FOREGROUND_FPS : BACKGROUND_FPS;
        
        if (targetFPS != currentTargetFPS) {
            currentTargetFPS = targetFPS;
            updateTargetFPS();
        }
        
        // Calculate current FPS
        long currentTime = System.nanoTime();
        long elapsed = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
        
        if (elapsed > 0) {
            currentFPS = (int) (1_000_000_000L / elapsed);
        }
        
        // Limit FPS
        if (frameTimeNanos > 0 && elapsed < frameTimeNanos) {
            try {
                long sleepTime = (frameTimeNanos - elapsed) / 1_000_000L;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private void updateTargetFPS() {
        frameTimeNanos = 1_000_000_000L / currentTargetFPS;
        LOGGER.debug("Target FPS set to: {}", currentTargetFPS);
    }
    
    public int getCurrentFPS() {
        return currentFPS;
    }
    
    public int getTargetFPS() {
        return currentTargetFPS;
    }
}
