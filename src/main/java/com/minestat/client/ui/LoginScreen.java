package com.minestat.client.ui;

import com.minestat.client.MinestatClient;
import com.minestat.client.auth.AuthenticationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

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
    
    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JCheckBox rememberMeCheckbox;
    private JRadioButton offlineModeRadio;
    private JRadioButton premiumModeRadio;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    private JPanel registerPanel;
    
    public LoginScreen() {
        super("MinestatClient - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setSize(450, 400);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Title
        JLabel titleLabel = new JLabel("MinestatClient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(versionLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Mode selection
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup modeGroup = new ButtonGroup();
        offlineModeRadio = new JRadioButton("Offline Mode", true);
        premiumModeRadio = new JRadioButton("Premium Mode");
        modeGroup.add(offlineModeRadio);
        modeGroup.add(premiumModeRadio);
        modePanel.add(offlineModeRadio);
        modePanel.add(premiumModeRadio);
        mainPanel.add(modePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Username field
        JPanel usernamePanel = new JPanel(new BorderLayout(5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        mainPanel.add(usernamePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        // Password field
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        // Register panel (for offline mode)
        registerPanel = new JPanel(new BorderLayout(5, 5));
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        registerPanel.add(emailLabel, BorderLayout.WEST);
        registerPanel.add(emailField, BorderLayout.CENTER);
        registerPanel.setVisible(false);
        mainPanel.add(registerPanel);
        
        // Remember me checkbox
        rememberMeCheckbox = new JCheckBox("Remember Me");
        rememberMeCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(rememberMeCheckbox);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        loginButton.setPreferredSize(new Dimension(120, 30));
        registerButton.setPreferredSize(new Dimension(120, 30));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(Color.RED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add action listeners
        offlineModeRadio.addActionListener(e -> switchMode(LoginMode.OFFLINE));
        premiumModeRadio.addActionListener(e -> switchMode(LoginMode.PREMIUM));
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> toggleRegisterMode());
        
        // Load saved credentials if remember me was enabled
        if (MinestatClient.getInstance().getConfigManager().getConfig().rememberMe) {
            usernameField.setText(MinestatClient.getInstance().getConfigManager().getConfig().lastUsername);
            rememberMeCheckbox.setSelected(true);
        }
    }
    
    @Override
    protected void onShow() {
        LOGGER.info("Login screen shown");
    }
    
    @Override
    protected void onHide() {
        LOGGER.info("Login screen hidden");
    }
    
    /**
     * Switch between offline and premium login modes
     */
    private void switchMode(LoginMode mode) {
        currentMode = mode;
        LOGGER.info("Switched to {} login mode", currentMode);
        
        // Show/hide register button and email field based on mode
        registerButton.setVisible(mode == LoginMode.OFFLINE);
        passwordField.setEnabled(mode == LoginMode.OFFLINE);
        
        if (mode == LoginMode.PREMIUM) {
            registerPanel.setVisible(false);
            statusLabel.setText("Click Login to open Microsoft authentication");
        } else {
            statusLabel.setText(" ");
        }
    }
    
    /**
     * Toggle register mode
     */
    private void toggleRegisterMode() {
        boolean isRegisterMode = !registerPanel.isVisible();
        registerPanel.setVisible(isRegisterMode);
        loginButton.setText(isRegisterMode ? "Back" : "Login");
        registerButton.setText(isRegisterMode ? "Create Account" : "Register");
        pack();
        centerWindow();
        
        if (isRegisterMode) {
            statusLabel.setText("Fill in email to create new account");
        } else {
            statusLabel.setText(" ");
        }
    }
    
    /**
     * Attempt login or registration
     */
    private void login() {
        if (registerPanel.isVisible()) {
            // Back to login mode
            toggleRegisterMode();
            return;
        }
        
        LOGGER.info("Attempting login...");
        statusLabel.setText("Logging in...");
        statusLabel.setForeground(Color.BLUE);
        
        AuthenticationResult result;
        
        if (currentMode == LoginMode.OFFLINE) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty()) {
                showError("Please enter a username");
                return;
            }
            
            result = MinestatClient.getInstance()
                                  .getAuthManager()
                                  .authenticateOffline(username, password, rememberMeCheckbox.isSelected());
        } else {
            // For Microsoft, open OAuth flow
            result = MinestatClient.getInstance()
                                  .getAuthManager()
                                  .authenticateMicrosoft(rememberMeCheckbox.isSelected());
        }
        
        if (result.isSuccess()) {
            LOGGER.info("Login successful!");
            
            // Save settings
            if (rememberMeCheckbox.isSelected()) {
                MinestatClient.getInstance().getConfigManager().getConfig().lastUsername = usernameField.getText();
                MinestatClient.getInstance().getConfigManager().getConfig().rememberMe = true;
                MinestatClient.getInstance().getConfigManager().save();
            }
            
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(0, 128, 0));
            
            // Go to main menu
            SwingUtilities.invokeLater(() -> {
                hide();
                MinestatClient.getInstance().getUIManager().showMainMenu();
            });
        } else {
            LOGGER.error("Login failed: {}", result.getMessage());
            showError(result.getMessage());
        }
    }
    
    /**
     * Show error message
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
}
