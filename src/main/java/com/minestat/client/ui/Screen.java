package com.minestat.client.ui;

/**
 * Base class for UI screens
 */
public abstract class Screen {
    
    protected boolean visible = false;
    
    /**
     * Show the screen
     */
    public void show() {
        visible = true;
        onShow();
    }
    
    /**
     * Hide the screen
     */
    public void hide() {
        visible = false;
        onHide();
    }
    
    /**
     * Check if screen is visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Called when screen is shown
     */
    protected abstract void onShow();
    
    /**
     * Called when screen is hidden
     */
    protected abstract void onHide();
    
    /**
     * Render the screen
     */
    public abstract void render(int mouseX, int mouseY, float partialTicks);
    
    /**
     * Handle mouse click
     */
    public abstract void handleMouseClick(int mouseX, int mouseY, int button);
    
    /**
     * Handle key press
     */
    public abstract void handleKeyPress(int keyCode, char typedChar);
}
