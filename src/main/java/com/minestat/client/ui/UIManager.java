package com.minestat.client.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * Manages all UI screens and navigation
 */
public class UIManager {
    
    private static final Logger LOGGER = LogManager.getLogger(UIManager.class);
    
    private LoginScreen loginScreen;
    private MainMenuScreen mainMenu;
    private SettingsScreen settingsScreen;
    
    private Screen currentScreen;
    private Screen previousScreen;
    
    public void initialize() {
        LOGGER.info("Initializing UI manager...");
        
        // Set system look and feel for Windows
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.warn("Failed to set system look and feel", e);
        }
        
        // Initialize screens on EDT
        SwingUtilities.invokeLater(() -> {
            loginScreen = new LoginScreen();
            mainMenu = new MainMenuScreen();
            settingsScreen = new SettingsScreen();
            
            LOGGER.info("UI manager initialized");
        });
    }
    
    /**
     * Show login screen
     */
    public void showLoginScreen() {
        SwingUtilities.invokeLater(() -> {
            LOGGER.info("Showing login screen");
            
            if (currentScreen != null) {
                currentScreen.hide();
            }
            
            previousScreen = currentScreen;
            currentScreen = loginScreen;
            loginScreen.show();
        });
    }
    
    /**
     * Show main menu
     */
    public void showMainMenu() {
        SwingUtilities.invokeLater(() -> {
            LOGGER.info("Showing main menu");
            
            if (currentScreen != null) {
                currentScreen.hide();
            }
            
            previousScreen = currentScreen;
            currentScreen = mainMenu;
            mainMenu.show();
        });
    }
    
    /**
     * Show settings screen
     */
    public void showSettings() {
        SwingUtilities.invokeLater(() -> {
            LOGGER.info("Showing settings screen");
            
            if (currentScreen != null && currentScreen != mainMenu) {
                currentScreen.hide();
            }
            
            previousScreen = currentScreen;
            currentScreen = settingsScreen;
            settingsScreen.show();
        });
    }
    
    /**
     * Go back to previous screen
     */
    public void goBack() {
        SwingUtilities.invokeLater(() -> {
            if (currentScreen != null) {
                currentScreen.hide();
            }
            
            // Default back to main menu if no previous screen
            if (previousScreen == null || previousScreen == loginScreen) {
                showMainMenu();
            } else {
                currentScreen = previousScreen;
                previousScreen = null;
                currentScreen.show();
            }
        });
    }
    
    /**
     * Get current screen
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }
}
