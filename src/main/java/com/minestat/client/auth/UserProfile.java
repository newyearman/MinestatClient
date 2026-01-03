package com.minestat.client.auth;

import java.util.UUID;

/**
 * Represents a user profile
 */
public class UserProfile {
    
    private final String uuid;
    private final String username;
    private final String email;
    private final boolean premium;
    private final AccountType accountType;
    
    public enum AccountType {
        OFFLINE,
        MICROSOFT,
        MOJANG
    }
    
    public UserProfile(String uuid, String username, String email, boolean premium, AccountType accountType) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.premium = premium;
        this.accountType = accountType;
    }
    
    public static UserProfile createOffline(String username, String email) {
        // Generate UUID from username for offline mode
        String uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes()).toString();
        return new UserProfile(uuid, username, email, false, AccountType.OFFLINE);
    }
    
    public static UserProfile createPremium(String uuid, String username, AccountType type) {
        return new UserProfile(uuid, username, null, true, type);
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isPremium() {
        return premium;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
}
