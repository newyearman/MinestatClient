package com.minestat.client.ui;

import com.minestat.client.MinestatClient;
import com.minestat.client.auth.AuthenticationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Login screen with dual authentication support (Offline/Premium)
 */
public class LoginScreen extends Screen {
    
    private static final Logger LOGGER = LogManager.getLogger(LoginScreen.class);
    
    private enum LoginMode {
        OFFLINE,
        PREMIUM
    }
    
    private LoginMode currentMode = LoginMode.OFFLINE;
    
    // UI Elements (in real implementation, these would be actual UI components)
    private String usernameField = "";
    private String passwordField = "";
    private String emailField = "";
    private boolean rememberMe = false;
    private boolean showRegister = false;
    
    @Override
    protected void onShow() {
        LOGGER.info("Login screen shown");
        
        // Load saved credentials if remember me was enabled
        if (MinestatClient.getInstance().getConfigManager().getConfig().rememberMe) {
            usernameField = MinestatClient.getInstance().getConfigManager().getConfig().lastUsername;
        }
    }
    
    @Override
    protected void onHide() {
        LOGGER.info("Login screen hidden");
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        
        // In real implementation:
        // - Draw background with animation
        // - Draw title "MinestatClient"
        // - Draw mode switcher (Offline/Premium)
        // - Draw input fields
        // - Draw login/register buttons
        // - Draw remember me checkbox
        
        LOGGER.debug("Rendering login screen (mode: {})", currentMode);
    }
    
    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        // Handle button clicks
        // - Mode switcher
        // - Login button
        // - Register button (offline mode)
        // - Remember me checkbox
    }
    
    @Override
    public void handleKeyPress(int keyCode, char typedChar) {
        // Handle text input for fields
        // Handle Enter key to submit
    }
    
    /**
     * Switch between offline and premium login modes
     */
    public void switchMode() {
        currentMode = (currentMode == LoginMode.OFFLINE) ? LoginMode.PREMIUM : LoginMode.OFFLINE;
        LOGGER.info("Switched to {} login mode", currentMode);
    }
    
    /**
     * Attempt login
     */
    public void login() {
        LOGGER.info("Attempting login...");
        
        AuthenticationResult result;
        
        if (currentMode == LoginMode.OFFLINE) {
            result = MinestatClient.getInstance()
                                  .getAuthManager()
                                  .authenticateOffline(usernameField, passwordField, rememberMe);
        } else {
            // For Microsoft, open OAuth flow
            result = MinestatClient.getInstance()
                                  .getAuthManager()
                                  .authenticateMicrosoft(rememberMe);
        }
        
        if (result.isSuccess()) {
            LOGGER.info("Login successful!");
            
            // Save settings
            if (rememberMe) {
                MinestatClient.getInstance().getConfigManager().getConfig().lastUsername = usernameField;
                MinestatClient.getInstance().getConfigManager().getConfig().rememberMe = true;
                MinestatClient.getInstance().getConfigManager().save();
            }
            
            // Go to main menu
            MinestatClient.getInstance().getUIManager().showMainMenu();
        } else {
            LOGGER.error("Login failed: {}", result.getMessage());
            // Show error message to user
        }
    }
    
    /**
     * Show register form (offline mode only)
     */
    public void showRegisterForm() {
        showRegister = true;
    }
    
    /**
     * Register new offline account
     */
    public void register() {
        LOGGER.info("Attempting registration...");
        
        AuthenticationResult result = MinestatClient.getInstance()
                                                   .getAuthManager()
                                                   .registerOffline(usernameField, passwordField, emailField);
        
        if (result.isSuccess()) {
            LOGGER.info("Registration successful!");
            // Auto-logged in, go to main menu
            MinestatClient.getInstance().getUIManager().showMainMenu();
        } else {
            LOGGER.error("Registration failed: {}", result.getMessage());
            // Show error message
        }
    }
}
