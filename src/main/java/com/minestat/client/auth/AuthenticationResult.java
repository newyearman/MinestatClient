package com.minestat.client.auth;

/**
 * Result of an authentication attempt
 */
public class AuthenticationResult {
    
    private final boolean success;
    private final String message;
    private final UserProfile profile;
    private final String accessToken;
    private final String refreshToken;
    
    private AuthenticationResult(boolean success, String message, UserProfile profile, 
                                 String accessToken, String refreshToken) {
        this.success = success;
        this.message = message;
        this.profile = profile;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
    public static AuthenticationResult success(UserProfile profile, String accessToken, String refreshToken) {
        return new AuthenticationResult(true, "Authentication successful", profile, accessToken, refreshToken);
    }
    
    public static AuthenticationResult failure(String message) {
        return new AuthenticationResult(false, message, null, null, null);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public UserProfile getProfile() {
        return profile;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
}
