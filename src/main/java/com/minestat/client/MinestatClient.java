package com.minestat.client;

import com.minestat.client.auth.AuthenticationManager;
import com.minestat.client.config.ConfigManager;
import com.minestat.client.cosmetics.CosmeticManager;
import com.minestat.client.optimization.OptimizationManager;
import com.minestat.client.ui.UIManager;
import com.minestat.client.version.VersionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class for MinestatClient
 * A professional, full-featured Minecraft client with multi-version support,
 * dual authentication, cosmetics, optimization, and internationalization.
 * 
 * @author MinestatClient Team
 * @version 1.0.0
 */
public class MinestatClient {
    
    public static final String CLIENT_NAME = "MinestatClient";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger(CLIENT_NAME);
    
    private static MinestatClient instance;
    
    // Core managers
    private ConfigManager configManager;
    private AuthenticationManager authManager;
    private VersionManager versionManager;
    private CosmeticManager cosmeticManager;
    private OptimizationManager optimizationManager;
    private UIManager uiManager;
    
    private boolean running = false;
    
    /**
     * Gets the singleton instance of MinestatClient (thread-safe)
     * @return MinestatClient instance
     */
    public static synchronized MinestatClient getInstance() {
        if (instance == null) {
            instance = new MinestatClient();
        }
        return instance;
    }
    
    /**
     * Initializes the client and all its managers
     */
    public void initialize() {
        LOGGER.info("Initializing {} v{}", CLIENT_NAME, VERSION);
        
        try {
            // Initialize managers in order of dependency
            LOGGER.info("Loading configuration...");
            configManager = new ConfigManager();
            configManager.load();
            
            LOGGER.info("Initializing authentication system...");
            authManager = new AuthenticationManager();
            authManager.initialize();
            
            LOGGER.info("Initializing version manager...");
            versionManager = new VersionManager();
            versionManager.initialize();
            
            LOGGER.info("Initializing cosmetic system...");
            cosmeticManager = new CosmeticManager();
            cosmeticManager.initialize();
            
            LOGGER.info("Initializing optimization system...");
            optimizationManager = new OptimizationManager();
            optimizationManager.initialize();
            
            LOGGER.info("Initializing UI manager...");
            uiManager = new UIManager();
            uiManager.initialize();
            
            running = true;
            LOGGER.info("{} initialized successfully!", CLIENT_NAME);
            
        } catch (Exception e) {
            LOGGER.error("Failed to initialize {}", CLIENT_NAME, e);
            throw new RuntimeException("Client initialization failed", e);
        }
    }
    
    /**
     * Starts the client
     */
    public void start() {
        if (!running) {
            LOGGER.warn("Cannot start client - not initialized!");
            return;
        }
        
        LOGGER.info("Starting {}...", CLIENT_NAME);
        
        // Show login screen
        uiManager.showLoginScreen();
    }
    
    /**
     * Shuts down the client gracefully
     */
    public void shutdown() {
        LOGGER.info("Shutting down {}...", CLIENT_NAME);
        
        try {
            if (optimizationManager != null) {
                optimizationManager.shutdown();
            }
            
            if (cosmeticManager != null) {
                cosmeticManager.shutdown();
            }
            
            if (authManager != null) {
                authManager.shutdown();
            }
            
            if (configManager != null) {
                configManager.save();
            }
            
            running = false;
            LOGGER.info("{} shut down successfully", CLIENT_NAME);
            
        } catch (Exception e) {
            LOGGER.error("Error during shutdown", e);
        }
    }
    
    // Getters for managers
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public AuthenticationManager getAuthManager() {
        return authManager;
    }
    
    public VersionManager getVersionManager() {
        return versionManager;
    }
    
    public CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }
    
    public OptimizationManager getOptimizationManager() {
        return optimizationManager;
    }
    
    public UIManager getUIManager() {
        return uiManager;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Main entry point
     */
    public static void main(String[] args) {
        try {
            MinestatClient client = MinestatClient.getInstance();
            client.initialize();
            client.start();
            
            // Add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                client.shutdown();
            }));
            
        } catch (Exception e) {
            LOGGER.fatal("Failed to start client", e);
            System.exit(1);
        }
    }
}
