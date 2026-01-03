package com.minestat.client.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages all UI screens and navigation
 */
public class UIManager {
    
    private static final Logger LOGGER = LogManager.getLogger(UIManager.class);
    
    private LoginScreen loginScreen;
    private MainMenuScreen mainMenu;
    private SettingsScreen settingsScreen;
    
    private Screen currentScreen;
    
    public void initialize() {
        LOGGER.info("Initializing UI manager...");
        
        loginScreen = new LoginScreen();
        mainMenu = new MainMenuScreen();
        settingsScreen = new SettingsScreen();
        
        LOGGER.info("UI manager initialized");
    }
    
    /**
     * Show login screen
     */
    public void showLoginScreen() {
        LOGGER.info("Showing login screen");
        currentScreen = loginScreen;
        loginScreen.show();
    }
    
    /**
     * Show main menu
     */
    public void showMainMenu() {
        LOGGER.info("Showing main menu");
        currentScreen = mainMenu;
        mainMenu.show();
    }
    
    /**
     * Show settings screen
     */
    public void showSettings() {
        LOGGER.info("Showing settings screen");
        currentScreen = settingsScreen;
        settingsScreen.show();
    }
    
    /**
     * Go back to previous screen
     */
    public void goBack() {
        if (currentScreen != null) {
            currentScreen.hide();
        }
        
        // Default back to main menu
        showMainMenu();
    }
    
    /**
     * Get current screen
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }
}
