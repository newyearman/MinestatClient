package com.minestat.client.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minestat.client.MinestatClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;

/**
 * Manages user sessions and "Remember Me" functionality
 */
public class SessionManager {
    
    private static final Logger LOGGER = LogManager.getLogger(SessionManager.class);
    private static final String SESSION_FILE = "config/session.json";
    
    private final Gson gson;
    
    public SessionManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Save session for "Remember Me" functionality
     */
    public void saveSession(AuthenticationResult auth) {
        try {
            Path sessionPath = Paths.get(SESSION_FILE);
            Files.createDirectories(sessionPath.getParent());
            
            SessionData data = new SessionData();
            data.username = auth.getProfile().getUsername();
            data.uuid = auth.getProfile().getUuid();
            data.accessToken = auth.getAccessToken();
            data.refreshToken = auth.getRefreshToken();
            data.accountType = auth.getProfile().getAccountType().name();
            data.timestamp = System.currentTimeMillis();
            
            try (Writer writer = Files.newBufferedWriter(sessionPath)) {
                gson.toJson(data, writer);
            }
            
            LOGGER.info("Session saved for user: {}", data.username);
            
        } catch (Exception e) {
            LOGGER.error("Failed to save session", e);
        }
    }
    
    /**
     * Restore a previously saved session
     */
    public AuthenticationResult restoreSession() {
        try {
            Path sessionPath = Paths.get(SESSION_FILE);
            
            if (!Files.exists(sessionPath)) {
                LOGGER.info("No saved session found");
                return null;
            }
            
            try (Reader reader = Files.newBufferedReader(sessionPath)) {
                SessionData data = gson.fromJson(reader, SessionData.class);
                
                // Check if session is not too old (7 days)
                long age = System.currentTimeMillis() - data.timestamp;
                if (age > 7 * 24 * 60 * 60 * 1000L) {
                    LOGGER.info("Session expired");
                    clearSession();
                    return null;
                }
                
                // Create user profile from saved data
                UserProfile.AccountType type = UserProfile.AccountType.valueOf(data.accountType);
                UserProfile profile;
                
                if (type == UserProfile.AccountType.OFFLINE) {
                    profile = UserProfile.createOffline(data.username, null);
                } else {
                    profile = UserProfile.createPremium(data.uuid, data.username, type);
                }
                
                LOGGER.info("Session restored for user: {}", data.username);
                return AuthenticationResult.success(profile, data.accessToken, data.refreshToken);
            }
            
        } catch (Exception e) {
            LOGGER.error("Failed to restore session", e);
            clearSession();
            return null;
        }
    }
    
    /**
     * Clear saved session
     */
    public void clearSession() {
        try {
            Path sessionPath = Paths.get(SESSION_FILE);
            Files.deleteIfExists(sessionPath);
            LOGGER.info("Session cleared");
        } catch (Exception e) {
            LOGGER.error("Failed to clear session", e);
        }
    }
    
    private static class SessionData {
        String username;
        String uuid;
        String accessToken;
        String refreshToken;
        String accountType;
        long timestamp;
    }
}
