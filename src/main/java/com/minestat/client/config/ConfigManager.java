package com.minestat.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;

/**
 * Manages client configuration using JSON format
 */
public class ConfigManager {
    
    private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);
    private static final String CONFIG_DIR = "config";
    private static final String CONFIG_FILE = "minestat_client.json";
    
    private final Gson gson;
    private ClientConfig config;
    private Path configPath;
    
    public ConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.configPath = Paths.get(CONFIG_DIR, CONFIG_FILE);
    }
    
    /**
     * Loads configuration from file or creates default
     */
    public void load() {
        try {
            // Create config directory if it doesn't exist
            Files.createDirectories(configPath.getParent());
            
            if (Files.exists(configPath)) {
                LOGGER.info("Loading config from: {}", configPath);
                try (Reader reader = Files.newBufferedReader(configPath)) {
                    config = gson.fromJson(reader, ClientConfig.class);
                }
            } else {
                LOGGER.info("Config file not found, creating default");
                config = ClientConfig.createDefault();
                save();
            }
            
        } catch (Exception e) {
            LOGGER.error("Failed to load config, using defaults", e);
            config = ClientConfig.createDefault();
        }
    }
    
    /**
     * Saves configuration to file
     */
    public void save() {
        try {
            Files.createDirectories(configPath.getParent());
            
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                gson.toJson(config, writer);
            }
            
            LOGGER.info("Config saved to: {}", configPath);
            
        } catch (Exception e) {
            LOGGER.error("Failed to save config", e);
        }
    }
    
    public ClientConfig getConfig() {
        return config;
    }
    
    /**
     * Client configuration data structure
     */
    public static class ClientConfig {
        // General settings
        public String language = "en_US";
        public String selectedVersion = "1.20.1";
        public boolean rememberMe = false;
        public String lastUsername = "";
        
        // Video settings
        public VideoSettings video = new VideoSettings();
        
        // Performance settings
        public PerformanceSettings performance = new PerformanceSettings();
        
        // Cosmetic settings
        public CosmeticSettings cosmetics = new CosmeticSettings();
        
        // Authentication settings
        public AuthSettings auth = new AuthSettings();
        
        public static ClientConfig createDefault() {
            return new ClientConfig();
        }
    }
    
    public static class VideoSettings {
        public boolean vsync = true;
        public int renderDistance = 12;
        public boolean shadows = true;
        public int particleDensity = 100;
        public boolean smoothLighting = true;
        public int guiScale = 0; // Auto
        public int maxFps = 120;
    }
    
    public static class PerformanceSettings {
        public boolean dynamicFps = true;
        public boolean fastRender = true;
        public boolean smoothFps = true;
        public boolean chunkOptimization = true;
        public int entityRenderDistance = 64;
        public boolean particleOptimization = true;
    }
    
    public static class CosmeticSettings {
        public String selectedCape = "none_cape";
        public String selectedWings = "none_wings";
        public String selectedHat = "none_hat";
        public String selectedParticle = "none_particle";
        public boolean showCosmetics = true;
    }
    
    public static class AuthSettings {
        public String authMode = "offline"; // offline or premium
        public boolean autoLogin = false;
        public String savedToken = "";
    }
}
