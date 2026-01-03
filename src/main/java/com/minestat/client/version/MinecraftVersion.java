package com.minestat.client.version;

/**
 * Represents a Minecraft version
 */
public class MinecraftVersion {
    
    private final String id;
    private final String name;
    private final VersionType type;
    
    public MinecraftVersion(String id, String name, VersionType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public VersionType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
