package com.minestat.client.version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Manages Minecraft version support and switching
 */
public class VersionManager {
    
    private static final Logger LOGGER = LogManager.getLogger(VersionManager.class);
    
    private final Map<String, MinecraftVersion> supportedVersions;
    private MinecraftVersion currentVersion;
    
    public VersionManager() {
        this.supportedVersions = new LinkedHashMap<>();
    }
    
    public void initialize() {
        LOGGER.info("Initializing version manager...");
        
        // Register supported versions
        registerVersion(new MinecraftVersion("1.8.9", "1.8.9", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.12.2", "1.12.2", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.16.5", "1.16.5", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.18.2", "1.18.2", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.19.4", "1.19.4", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.20.1", "1.20.1", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.20.2", "1.20.2", VersionType.RELEASE));
        registerVersion(new MinecraftVersion("1.20.4", "1.20.4", VersionType.RELEASE));
        
        // Set default version
        currentVersion = supportedVersions.get("1.20.1");
        
        LOGGER.info("Version manager initialized with {} supported versions", supportedVersions.size());
    }
    
    private void registerVersion(MinecraftVersion version) {
        supportedVersions.put(version.getId(), version);
        LOGGER.debug("Registered version: {}", version.getId());
    }
    
    /**
     * Set the active Minecraft version
     */
    public boolean setVersion(String versionId) {
        MinecraftVersion version = supportedVersions.get(versionId);
        
        if (version != null) {
            currentVersion = version;
            LOGGER.info("Switched to Minecraft version: {}", versionId);
            return true;
        } else {
            LOGGER.warn("Unknown version: {}", versionId);
            return false;
        }
    }
    
    /**
     * Get current version
     */
    public MinecraftVersion getCurrentVersion() {
        return currentVersion;
    }
    
    /**
     * Get all supported versions
     */
    public List<MinecraftVersion> getSupportedVersions() {
        return new ArrayList<>(supportedVersions.values());
    }
    
    /**
     * Check if a version is supported
     */
    public boolean isVersionSupported(String versionId) {
        return supportedVersions.containsKey(versionId);
    }
    
    /**
     * Get version by ID
     */
    public MinecraftVersion getVersion(String versionId) {
        return supportedVersions.get(versionId);
    }
}
