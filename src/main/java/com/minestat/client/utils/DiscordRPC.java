package com.minestat.client.utils;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Discord Rich Presence integration
 */
public class DiscordRPC {
    
    private static final Logger LOGGER = LogManager.getLogger(DiscordRPC.class);
    private static final long CLIENT_ID = 123456789012345678L; // Replace with actual Discord app ID
    
    private static IPCClient client;
    private static boolean enabled = false;
    
    /**
     * Initialize Discord RPC
     */
    public static void initialize() {
        try {
            client = new IPCClient(CLIENT_ID);
            
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    LOGGER.info("Discord RPC ready");
                    enabled = true;
                    updatePresence("In Menu", "Idle");
                }
            });
            
            client.connect();
            
        } catch (Exception e) {
            LOGGER.warn("Failed to initialize Discord RPC", e);
        }
    }
    
    /**
     * Update Discord presence
     */
    public static void updatePresence(String state, String details) {
        if (!enabled || client == null) {
            return;
        }
        
        try {
            RichPresence.Builder builder = new RichPresence.Builder();
            builder.setState(state)
                   .setDetails(details)
                   .setLargeImage("minestat_logo", "MinestatClient")
                   .setStartTimestamp(System.currentTimeMillis());
            
            client.sendRichPresence(builder.build());
            
        } catch (Exception e) {
            LOGGER.error("Failed to update Discord presence", e);
        }
    }
    
    /**
     * Update presence for in-game
     */
    public static void updateInGame(String serverName, int playerCount) {
        if (!enabled) {
            return;
        }
        
        String details = serverName != null ? "Playing on " + serverName : "Singleplayer";
        String state = playerCount > 0 ? playerCount + " players online" : "Solo";
        
        updatePresence(state, details);
    }
    
    /**
     * Shutdown Discord RPC
     */
    public static void shutdown() {
        if (client != null) {
            try {
                client.close();
                LOGGER.info("Discord RPC shutdown");
            } catch (Exception e) {
                LOGGER.error("Error shutting down Discord RPC", e);
            }
        }
    }
}
