package com.minestat.client.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.sql.*;

/**
 * Provides offline/crack authentication using SQLite database
 */
public class OfflineAuthProvider {
    
    private static final Logger LOGGER = LogManager.getLogger(OfflineAuthProvider.class);
    private static final String DB_FILE = "config/users.db";
    
    private Connection connection;
    
    public OfflineAuthProvider() {
        try {
            // Initialize SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
            createTables();
        } catch (SQLException e) {
            LOGGER.error("Failed to initialize database", e);
        }
    }
    
    private void createTables() throws SQLException {
        String createUsersTable = 
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT UNIQUE NOT NULL," +
            "password_hash TEXT NOT NULL," +
            "email TEXT," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "last_login TIMESTAMP" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
        }
    }
    
    /**
     * Authenticate user with username and password
     */
    public AuthenticationResult authenticate(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString("password_hash");
                        String email = rs.getString("email");
                        
                        // Verify password
                        if (verifyPassword(password, storedHash)) {
                            // Update last login
                            updateLastLogin(username);
                            
                            UserProfile profile = UserProfile.createOffline(username, email);
                            return AuthenticationResult.success(profile, generateToken(username), null);
                        } else {
                            return AuthenticationResult.failure("Invalid password");
                        }
                    } else {
                        return AuthenticationResult.failure("User not found");
                    }
                }
            }
            
        } catch (SQLException e) {
            LOGGER.error("Authentication failed", e);
            return AuthenticationResult.failure("Database error: " + e.getMessage());
        }
    }
    
    /**
     * Register a new user
     */
    public AuthenticationResult register(String username, String password, String email) {
        try {
            // Check if username already exists
            if (userExists(username)) {
                return AuthenticationResult.failure("Username already exists");
            }
            
            // Hash password
            String passwordHash = hashPassword(password);
            
            // Insert new user
            String insert = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
            
            try (PreparedStatement stmt = connection.prepareStatement(insert)) {
                stmt.setString(1, username);
                stmt.setString(2, passwordHash);
                stmt.setString(3, email);
                
                stmt.executeUpdate();
            }
            
            LOGGER.info("User registered successfully: {}", username);
            
            // Auto-login after registration
            return authenticate(username, password);
            
        } catch (SQLException e) {
            LOGGER.error("Registration failed", e);
            return AuthenticationResult.failure("Registration failed: " + e.getMessage());
        }
    }
    
    private boolean userExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    private void updateLastLogin(String username) {
        try {
            String update = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE username = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(update)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn("Failed to update last login", e);
        }
    }
    
    /**
     * Hash password using SHA-256
     * Note: In production, use bcrypt or PBKDF2 with salt for better security
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (Exception e) {
            LOGGER.error("Failed to hash password", e);
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    private boolean verifyPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
    
    private String generateToken(String username) {
        // Simple token generation (in production, use proper JWT or similar)
        return hashPassword(username + System.currentTimeMillis());
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to close database connection", e);
        }
    }
}
