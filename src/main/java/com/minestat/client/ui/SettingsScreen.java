package com.minestat.client.ui;

import com.minestat.client.MinestatClient;
import com.minestat.client.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Settings screen with multiple categories
 */
public class SettingsScreen extends Screen {
    
    private static final Logger LOGGER = LogManager.getLogger(SettingsScreen.class);
    
    private JTabbedPane tabbedPane;
    
    // Video settings components
    private JCheckBox vsyncCheckbox;
    private JSpinner renderDistanceSpinner;
    private JCheckBox shadowsCheckbox;
    private JSpinner maxFpsSpinner;
    private JSpinner particleDensitySpinner;
    
    // Performance settings components
    private JCheckBox dynamicFpsCheckbox;
    private JCheckBox fastRenderCheckbox;
    private JCheckBox chunkOptimizationCheckbox;
    private JSpinner entityRenderDistanceSpinner;
    
    // Language settings
    private JComboBox<String> languageComboBox;
    
    public SettingsScreen() {
        super("MinestatClient - Settings");
    }
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setSize(600, 500);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Video", createVideoPanel());
        tabbedPane.addTab("Performance", createPerformancePanel());
        tabbedPane.addTab("Language", createLanguagePanel());
        tabbedPane.addTab("Cosmetics", createCosmeticsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        
        saveButton.addActionListener(e -> saveSettings());
        cancelButton.addActionListener(e -> goBack());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Load current settings
        loadSettings();
    }
    
    /**
     * Create video settings panel
     */
    private JPanel createVideoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // VSync
        vsyncCheckbox = new JCheckBox("Enable VSync");
        panel.add(vsyncCheckbox);
        panel.add(Box.createVerticalStrut(10));
        
        // Render Distance
        JPanel renderDistancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        renderDistancePanel.add(new JLabel("Render Distance:"));
        renderDistanceSpinner = new JSpinner(new SpinnerNumberModel(12, 2, 32, 1));
        renderDistancePanel.add(renderDistanceSpinner);
        renderDistancePanel.add(new JLabel("chunks"));
        panel.add(renderDistancePanel);
        panel.add(Box.createVerticalStrut(10));
        
        // Shadows
        shadowsCheckbox = new JCheckBox("Enable Shadows");
        panel.add(shadowsCheckbox);
        panel.add(Box.createVerticalStrut(10));
        
        // Max FPS
        JPanel maxFpsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxFpsPanel.add(new JLabel("Max FPS:"));
        maxFpsSpinner = new JSpinner(new SpinnerNumberModel(120, 30, 300, 10));
        maxFpsPanel.add(maxFpsSpinner);
        panel.add(maxFpsPanel);
        panel.add(Box.createVerticalStrut(10));
        
        // Particle Density
        JPanel particlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        particlePanel.add(new JLabel("Particle Density:"));
        particleDensitySpinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 10));
        particlePanel.add(particleDensitySpinner);
        particlePanel.add(new JLabel("%"));
        panel.add(particlePanel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Create performance settings panel
     */
    private JPanel createPerformancePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Dynamic FPS
        dynamicFpsCheckbox = new JCheckBox("Dynamic FPS (reduces FPS when window is unfocused)");
        panel.add(dynamicFpsCheckbox);
        panel.add(Box.createVerticalStrut(10));
        
        // Fast Render
        fastRenderCheckbox = new JCheckBox("Fast Render (optimized rendering pipeline)");
        panel.add(fastRenderCheckbox);
        panel.add(Box.createVerticalStrut(10));
        
        // Chunk Optimization
        chunkOptimizationCheckbox = new JCheckBox("Chunk Optimization (intelligent chunk loading)");
        panel.add(chunkOptimizationCheckbox);
        panel.add(Box.createVerticalStrut(10));
        
        // Entity Render Distance
        JPanel entityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        entityPanel.add(new JLabel("Entity Render Distance:"));
        entityRenderDistanceSpinner = new JSpinner(new SpinnerNumberModel(64, 16, 128, 16));
        entityPanel.add(entityRenderDistanceSpinner);
        entityPanel.add(new JLabel("blocks"));
        panel.add(entityPanel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Create language settings panel
     */
    private JPanel createLanguagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        languagePanel.add(new JLabel("Select Language:"));
        
        String[] languages = {"English (en_US)", "Turkish (tr_TR)", "German (de_DE)", "French (fr_FR)", "Spanish (es_ES)"};
        languageComboBox = new JComboBox<>(languages);
        languagePanel.add(languageComboBox);
        
        panel.add(languagePanel);
        panel.add(Box.createVerticalStrut(20));
        
        JLabel infoLabel = new JLabel("<html>Change the language of the user interface.<br><br>" +
                                       "Supported languages:<br>" +
                                       "• English<br>" +
                                       "• Turkish<br>" +
                                       "• German<br>" +
                                       "• French<br>" +
                                       "• Spanish</html>");
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Create cosmetics settings panel
     */
    private JPanel createCosmeticsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Cosmetic Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        
        JCheckBox showCosmeticsCheckbox = new JCheckBox("Show Cosmetics in Game");
        showCosmeticsCheckbox.setSelected(true);
        panel.add(showCosmeticsCheckbox);
        panel.add(Box.createVerticalStrut(20));
        
        JLabel infoLabel = new JLabel("<html>Manage your cosmetic items:<br><br>" +
                                       "• Capes<br>" +
                                       "• Wings<br>" +
                                       "• Hats<br>" +
                                       "• Particle Effects<br>" +
                                       "• Emotes<br><br>" +
                                       "Go to the Cosmetics menu to equip items.</html>");
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Load current settings from config
     */
    private void loadSettings() {
        ConfigManager.ClientConfig config = MinestatClient.getInstance().getConfigManager().getConfig();
        
        // Video settings
        vsyncCheckbox.setSelected(config.video.vsync);
        renderDistanceSpinner.setValue(config.video.renderDistance);
        shadowsCheckbox.setSelected(config.video.shadows);
        maxFpsSpinner.setValue(config.video.maxFps);
        particleDensitySpinner.setValue(config.video.particleDensity);
        
        // Performance settings
        dynamicFpsCheckbox.setSelected(config.performance.dynamicFps);
        fastRenderCheckbox.setSelected(config.performance.fastRender);
        chunkOptimizationCheckbox.setSelected(config.performance.chunkOptimization);
        entityRenderDistanceSpinner.setValue(config.performance.entityRenderDistance);
        
        // Language settings
        String language = config.language;
        int langIndex = 0;
        switch (language) {
            case "en_US": langIndex = 0; break;
            case "tr_TR": langIndex = 1; break;
            case "de_DE": langIndex = 2; break;
            case "fr_FR": langIndex = 3; break;
            case "es_ES": langIndex = 4; break;
        }
        languageComboBox.setSelectedIndex(langIndex);
    }
    
    /**
     * Save settings to config
     */
    private void saveSettings() {
        ConfigManager.ClientConfig config = MinestatClient.getInstance().getConfigManager().getConfig();
        
        // Video settings
        config.video.vsync = vsyncCheckbox.isSelected();
        config.video.renderDistance = (Integer) renderDistanceSpinner.getValue();
        config.video.shadows = shadowsCheckbox.isSelected();
        config.video.maxFps = (Integer) maxFpsSpinner.getValue();
        config.video.particleDensity = (Integer) particleDensitySpinner.getValue();
        
        // Performance settings
        config.performance.dynamicFps = dynamicFpsCheckbox.isSelected();
        config.performance.fastRender = fastRenderCheckbox.isSelected();
        config.performance.chunkOptimization = chunkOptimizationCheckbox.isSelected();
        config.performance.entityRenderDistance = (Integer) entityRenderDistanceSpinner.getValue();
        
        // Language settings
        int langIndex = languageComboBox.getSelectedIndex();
        String[] langCodes = {"en_US", "tr_TR", "de_DE", "fr_FR", "es_ES"};
        config.language = langCodes[langIndex];
        
        // Save to file
        MinestatClient.getInstance().getConfigManager().save();
        
        LOGGER.info("Settings saved successfully");
        
        // Reinitialize optimization manager with new settings
        MinestatClient.getInstance().getOptimizationManager().shutdown();
        MinestatClient.getInstance().getOptimizationManager().initialize();
        
        JOptionPane.showMessageDialog(
            this,
            "Settings saved successfully!",
            "Settings",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        goBack();
    }
    
    @Override
    protected void onShow() {
        LOGGER.info("Settings screen shown");
    }
    
    @Override
    protected void onHide() {
        LOGGER.info("Settings screen hidden");
    }
    
    /**
     * Go back to main menu
     */
    private void goBack() {
        hide();
        MinestatClient.getInstance().getUIManager().goBack();
    }
}
