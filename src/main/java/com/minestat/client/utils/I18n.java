package com.minestat.client.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Manages internationalization (i18n) and language support
 */
public class I18n {
    
    private static final Logger LOGGER = LogManager.getLogger(I18n.class);
    
    private static final Map<String, LanguageData> languages = new HashMap<>();
    private static String currentLanguage = "en_US";
    
    static {
        // Register supported languages
        registerLanguage("en_US", "English");
        registerLanguage("tr_TR", "Türkçe");
        registerLanguage("de_DE", "Deutsch");
        registerLanguage("fr_FR", "Français");
        registerLanguage("es_ES", "Español");
    }
    
    private static void registerLanguage(String code, String name) {
        LanguageData data = new LanguageData(code, name);
        languages.put(code, data);
        LOGGER.debug("Registered language: {} ({})", name, code);
    }
    
    /**
     * Get translated string for key
     */
    public static String translate(String key) {
        LanguageData lang = languages.get(currentLanguage);
        
        if (lang != null && lang.translations.containsKey(key)) {
            return lang.translations.get(key);
        }
        
        // Fallback to English
        lang = languages.get("en_US");
        if (lang != null && lang.translations.containsKey(key)) {
            return lang.translations.get(key);
        }
        
        // Return key if no translation found
        return key;
    }
    
    /**
     * Format translated string with arguments
     */
    public static String format(String key, Object... args) {
        String translation = translate(key);
        return String.format(translation, args);
    }
    
    /**
     * Set current language
     */
    public static void setLanguage(String languageCode) {
        if (languages.containsKey(languageCode)) {
            currentLanguage = languageCode;
            LOGGER.info("Language set to: {}", languageCode);
        } else {
            LOGGER.warn("Unknown language code: {}", languageCode);
        }
    }
    
    /**
     * Get current language
     */
    public static String getCurrentLanguage() {
        return currentLanguage;
    }
    
    /**
     * Get all available languages
     */
    public static List<LanguageData> getAvailableLanguages() {
        return new ArrayList<>(languages.values());
    }
    
    /**
     * Load translations for a language
     */
    public static void loadTranslations(String languageCode, Map<String, String> translations) {
        LanguageData lang = languages.get(languageCode);
        
        if (lang != null) {
            lang.translations.putAll(translations);
            LOGGER.info("Loaded {} translations for {}", translations.size(), languageCode);
        }
    }
    
    /**
     * Language data holder
     */
    public static class LanguageData {
        public final String code;
        public final String name;
        public final Map<String, String> translations;
        
        public LanguageData(String code, String name) {
            this.code = code;
            this.name = name;
            this.translations = new HashMap<>();
            
            // Load default translations
            loadDefaults();
        }
        
        private void loadDefaults() {
            // Common translations (will be loaded from JSON in real implementation)
            if (code.equals("en_US")) {
                translations.put("menu.login", "Login");
                translations.put("menu.register", "Register");
                translations.put("menu.play", "Play");
                translations.put("menu.settings", "Settings");
                translations.put("menu.cosmetics", "Cosmetics");
                translations.put("menu.exit", "Exit");
                translations.put("settings.video", "Video Settings");
                translations.put("settings.language", "Language");
                translations.put("settings.performance", "Performance");
                translations.put("login.offline", "Offline Mode");
                translations.put("login.premium", "Premium Mode");
                translations.put("login.username", "Username");
                translations.put("login.password", "Password");
                translations.put("login.email", "Email");
                translations.put("login.remember", "Remember Me");
            } else if (code.equals("tr_TR")) {
                translations.put("menu.login", "Giriş Yap");
                translations.put("menu.register", "Kayıt Ol");
                translations.put("menu.play", "Oyna");
                translations.put("menu.settings", "Ayarlar");
                translations.put("menu.cosmetics", "Kozmetikler");
                translations.put("menu.exit", "Çıkış");
                translations.put("settings.video", "Video Ayarları");
                translations.put("settings.language", "Dil");
                translations.put("settings.performance", "Performans");
                translations.put("login.offline", "Çevrimdışı Mod");
                translations.put("login.premium", "Premium Mod");
                translations.put("login.username", "Kullanıcı Adı");
                translations.put("login.password", "Şifre");
                translations.put("login.email", "E-posta");
                translations.put("login.remember", "Beni Hatırla");
            }
        }
    }
}
