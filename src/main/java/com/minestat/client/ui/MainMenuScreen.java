package com.minestat.client.ui;

import com.minestat.client.MinestatClient;
import com.minestat.client.auth.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Main menu screen with modern UI
 */
public class MainMenuScreen extends Screen {
    
    private static final Logger LOGGER = LogManager.getLogger(MainMenuScreen.class);
    
    private JComboBox<String> versionSelector;
    private JLabel userLabel;
    
    public MainMenuScreen() {
        super("MinestatClient - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setSize(600, 500);
        
        // Top panel - User info and version selector
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // User info on left
        userLabel = new JLabel("Welcome, Player!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(userLabel, BorderLayout.WEST);
        
        // Version selector on right
        JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel versionLabel = new JLabel("Version:");
        String[] versions = {"1.20.1", "1.19.4", "1.18.2", "1.16.5", "1.12.2", "1.8.9"};
        versionSelector = new JComboBox<>(versions);
        versionSelector.setPreferredSize(new Dimension(100, 25));
        versionPanel.add(versionLabel);
        versionPanel.add(versionSelector);
        topPanel.add(versionPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Menu buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("MinestatClient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(titleLabel, gbc);
        
        // Add some spacing
        gbc.gridy++;
        centerPanel.add(Box.createVerticalStrut(30), gbc);
        
        // Play button
        JButton playButton = createMenuButton("Play");
        playButton.addActionListener(e -> playGame());
        gbc.gridy++;
        centerPanel.add(playButton, gbc);
        
        // Settings button
        JButton settingsButton = createMenuButton("Settings");
        settingsButton.addActionListener(e -> openSettings());
        gbc.gridy++;
        centerPanel.add(settingsButton, gbc);
        
        // Cosmetics button
        JButton cosmeticsButton = createMenuButton("Cosmetics");
        cosmeticsButton.addActionListener(e -> openCosmetics());
        gbc.gridy++;
        centerPanel.add(cosmeticsButton, gbc);
        
        // Exit button
        JButton exitButton = createMenuButton("Exit");
        exitButton.addActionListener(e -> exit());
        gbc.gridy++;
        centerPanel.add(exitButton, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel - Version info
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel versionInfoLabel = new JLabel("MinestatClient v1.0.0");
        versionInfoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        versionInfoLabel.setForeground(Color.GRAY);
        bottomPanel.add(versionInfoLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create a styled menu button
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 45));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        return button;
    }
    
    @Override
    protected void onShow() {
        LOGGER.info("Main menu shown");
        
        // Update user label with current user info
        if (MinestatClient.getInstance().getAuthManager().isAuthenticated()) {
            UserProfile profile = MinestatClient.getInstance().getAuthManager().getCurrentAuth().getProfile();
            if (profile != null) {
                userLabel.setText("Welcome, " + profile.getUsername() + "!");
            }
        }
        
        // Set selected version from config
        String currentVersion = MinestatClient.getInstance().getConfigManager().getConfig().selectedVersion;
        versionSelector.setSelectedItem(currentVersion);
    }
    
    @Override
    protected void onHide() {
        LOGGER.info("Main menu hidden");
    }
    
    /**
     * Launch the game with selected version
     */
    private void playGame() {
        LOGGER.info("Launching game...");
        
        String selectedVersion = (String) versionSelector.getSelectedItem();
        MinestatClient.getInstance().getConfigManager().getConfig().selectedVersion = selectedVersion;
        MinestatClient.getInstance().getConfigManager().save();
        
        LOGGER.info("Starting Minecraft {}", selectedVersion);
        
        JOptionPane.showMessageDialog(
            this,
            "Would launch Minecraft " + selectedVersion + "\n\nIn production, this would:\n" +
            "- Validate authentication\n" +
            "- Download game assets if needed\n" +
            "- Initialize Minecraft\n" +
            "- Start the game",
            "Game Launch",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Show settings menu
     */
    private void openSettings() {
        LOGGER.info("Opening settings");
        MinestatClient.getInstance().getUIManager().showSettings();
    }
    
    /**
     * Show cosmetics menu
     */
    private void openCosmetics() {
        LOGGER.info("Opening cosmetics menu");
        JOptionPane.showMessageDialog(
            this,
            "Cosmetics system is available!\n\n" +
            "Available cosmetics:\n" +
            "- Capes\n" +
            "- Wings\n" +
            "- Hats\n" +
            "- Particle Effects\n" +
            "- Emotes\n\n" +
            "This would open the cosmetics selection screen.",
            "Cosmetics",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exit client
     */
    private void exit() {
        LOGGER.info("Exiting client");
        
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit?",
            "Exit",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            MinestatClient.getInstance().shutdown();
            System.exit(0);
        }
    }
}
