package com.minestat.client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Base class for UI screens using Swing
 */
public abstract class Screen extends JFrame {
    
    protected boolean initialized = false;
    
    public Screen(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    /**
     * Initialize the screen components
     */
    protected abstract void initComponents();
    
    /**
     * Show the screen
     */
    public void show() {
        if (!initialized) {
            initComponents();
            initialized = true;
            pack();
            centerWindow();
        }
        onShow();
        setVisible(true);
    }
    
    /**
     * Hide the screen
     */
    public void hide() {
        setVisible(false);
        onHide();
    }
    
    /**
     * Center window on screen
     */
    protected void centerWindow() {
        setLocationRelativeTo(null);
    }
    
    /**
     * Called when screen is shown
     */
    protected abstract void onShow();
    
    /**
     * Called when screen is hidden
     */
    protected abstract void onHide();
}
