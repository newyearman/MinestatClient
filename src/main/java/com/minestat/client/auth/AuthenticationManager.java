package com.minestat.client.auth;

import com.minestat.client.MinestatClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages authentication for both offline (crack) and premium (Microsoft/Mojang) accounts
 */
public class AuthenticationManager {
    
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationManager.class);
    
    private OfflineAuthProvider offlineAuth;
    private PremiumAuthProvider premiumAuth;
    private SessionManager sessionManager;
    
    private AuthenticationResult currentAuth;
    
    public void initialize() {
        LOGGER.info("Initializing authentication system...");
        
        offlineAuth = new OfflineAuthProvider();
        premiumAuth = new PremiumAuthProvider();
        sessionManager = new SessionManager();
        
        // Try to restore session if remember me is enabled
        if (MinestatClient.getInstance().getConfigManager().getConfig().rememberMe) {
            LOGGER.info("Attempting to restore previous session...");
            currentAuth = sessionManager.restoreSession();
        }
    }
    
    /**
     * Authenticate using offline/crack mode
     */
    public AuthenticationResult authenticateOffline(String username, String password, boolean rememberMe) {
        LOGGER.info("Attempting offline authentication for user: {}", username);
        
        try {
            currentAuth = offlineAuth.authenticate(username, password);
            
            if (currentAuth.isSuccess()) {
                LOGGER.info("Offline authentication successful");
                if (rememberMe) {
                    sessionManager.saveSession(currentAuth);
                }
            }
            
            return currentAuth;
            
        } catch (Exception e) {
            LOGGER.error("Offline authentication failed", e);
            return AuthenticationResult.failure("Authentication failed: " + e.getMessage());
        }
    }
    
    /**
     * Authenticate using Microsoft OAuth
     */
    public AuthenticationResult authenticateMicrosoft(boolean rememberMe) {
        LOGGER.info("Attempting Microsoft authentication...");
        
        try {
            currentAuth = premiumAuth.authenticateMicrosoft();
            
            if (currentAuth.isSuccess()) {
                LOGGER.info("Microsoft authentication successful");
                if (rememberMe) {
                    sessionManager.saveSession(currentAuth);
                }
            }
            
            return currentAuth;
            
        } catch (Exception e) {
            LOGGER.error("Microsoft authentication failed", e);
            return AuthenticationResult.failure("Authentication failed: " + e.getMessage());
        }
    }
    
    /**
     * Authenticate using legacy Mojang account
     */
    public AuthenticationResult authenticateMojang(String email, String password, boolean rememberMe) {
        LOGGER.info("Attempting Mojang authentication for: {}", email);
        
        try {
            currentAuth = premiumAuth.authenticateMojang(email, password);
            
            if (currentAuth.isSuccess()) {
                LOGGER.info("Mojang authentication successful");
                if (rememberMe) {
                    sessionManager.saveSession(currentAuth);
                }
            }
            
            return currentAuth;
            
        } catch (Exception e) {
            LOGGER.error("Mojang authentication failed", e);
            return AuthenticationResult.failure("Authentication failed: " + e.getMessage());
        }
    }
    
    /**
     * Register a new offline account
     */
    public AuthenticationResult registerOffline(String username, String password, String email) {
        LOGGER.info("Attempting to register new offline account: {}", username);
        
        try {
            return offlineAuth.register(username, password, email);
        } catch (Exception e) {
            LOGGER.error("Registration failed", e);
            return AuthenticationResult.failure("Registration failed: " + e.getMessage());
        }
    }
    
    /**
     * Logout current user
     */
    public void logout() {
        LOGGER.info("Logging out current user");
        sessionManager.clearSession();
        currentAuth = null;
    }
    
    /**
     * Check if user is currently authenticated
     */
    public boolean isAuthenticated() {
        return currentAuth != null && currentAuth.isSuccess();
    }
    
    /**
     * Get current authentication result
     */
    public AuthenticationResult getCurrentAuth() {
        return currentAuth;
    }
    
    public void shutdown() {
        LOGGER.info("Shutting down authentication manager");
        if (offlineAuth != null) {
            offlineAuth.close();
        }
    }
}
