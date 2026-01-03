package com.minestat.client.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manages screenshots taken in-game
 */
public class ScreenshotManager {
    
    private static final Logger LOGGER = LogManager.getLogger(ScreenshotManager.class);
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    
    /**
     * Save a screenshot
     */
    public static File saveScreenshot(byte[] imageData, int width, int height) {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(screenshotPath);
            
            String filename = DATE_FORMAT.format(new Date()) + ".png";
            Path filePath = screenshotPath.resolve(filename);
            
            // In real implementation, convert imageData to PNG and save
            Files.write(filePath, imageData);
            
            LOGGER.info("Screenshot saved: {}", filename);
            return filePath.toFile();
            
        } catch (IOException e) {
            LOGGER.error("Failed to save screenshot", e);
            return null;
        }
    }
    
    /**
     * Get all screenshots
     */
    public static List<File> getScreenshots() {
        List<File> screenshots = new ArrayList<>();
        
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            
            if (Files.exists(screenshotPath)) {
                Files.walk(screenshotPath, 1)
                     .filter(Files::isRegularFile)
                     .filter(p -> p.toString().endsWith(".png"))
                     .forEach(p -> screenshots.add(p.toFile()));
            }
            
            // Sort by date (newest first)
            screenshots.sort((a, b) -> Long.compare(b.lastModified(), a.lastModified()));
            
        } catch (IOException e) {
            LOGGER.error("Failed to list screenshots", e);
        }
        
        return screenshots;
    }
    
    /**
     * Delete a screenshot
     */
    public static boolean deleteScreenshot(File screenshot) {
        try {
            Files.delete(screenshot.toPath());
            LOGGER.info("Screenshot deleted: {}", screenshot.getName());
            return true;
        } catch (IOException e) {
            LOGGER.error("Failed to delete screenshot", e);
            return false;
        }
    }
    
    /**
     * Open screenshots folder
     */
    public static void openScreenshotsFolder() {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(screenshotPath);
            
            // Open in file explorer
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("explorer " + screenshotPath.toAbsolutePath());
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec("open " + screenshotPath.toAbsolutePath());
            } else {
                Runtime.getRuntime().exec("xdg-open " + screenshotPath.toAbsolutePath());
            }
            
        } catch (IOException e) {
            LOGGER.error("Failed to open screenshots folder", e);
        }
    }
}
