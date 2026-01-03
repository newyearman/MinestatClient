# MinestatClient User Guide

Welcome to MinestatClient! This guide will help you get started and make the most of all features.

## Table of Contents
1. [Installation](#installation)
2. [First Launch](#first-launch)
3. [Authentication](#authentication)
4. [Using Cosmetics](#using-cosmetics)
5. [Performance Settings](#performance-settings)
6. [Language Settings](#language-settings)
7. [Advanced Features](#advanced-features)
8. [Troubleshooting](#troubleshooting)

## Installation

### System Requirements
- **Java**: Version 8 or higher
- **Operating System**: Windows, macOS, or Linux
- **RAM**: Minimum 2GB, Recommended 4GB+
- **Disk Space**: 500MB+

### Download and Install
1. Download the latest release from the [Releases](https://github.com/newyearman/MinestatClient/releases) page
2. Extract the ZIP file to your desired location
3. Run `MinestatClient.jar` or use the launcher script

## First Launch

On first launch, MinestatClient will:
1. Create necessary directories (`config/`, `screenshots/`, etc.)
2. Generate default configuration files
3. Show the login screen

## Authentication

MinestatClient supports two authentication modes:

### Offline/Crack Mode

Perfect for testing or offline play:

1. Click "Switch Mode" to select **Offline Mode**
2. For existing account:
   - Enter your username and password
   - Check "Remember Me" to stay logged in
   - Click "Login"
3. For new account:
   - Click "Register"
   - Enter username, password, and email
   - Click "Register" to create account

**Security Note**: Passwords are hashed using SHA-256 and stored locally in SQLite database.

### Premium Mode

For legitimate Minecraft accounts:

1. Click "Switch Mode" to select **Premium Mode**
2. Choose authentication method:
   - **Microsoft Account** (Recommended): Opens browser for OAuth authentication
   - **Mojang Account** (Legacy): Enter email and password directly
3. Check "Remember Me" to save your session
4. Complete authentication in browser (for Microsoft) or click Login

**Note**: Microsoft authentication uses OAuth 2.0 for secure login without storing passwords.

## Using Cosmetics

### Accessing Cosmetics

1. Log in to your account
2. Click "Cosmetics" from the main menu
3. Browse available cosmetics in your inventory

### Types of Cosmetics

#### Capes
- Classic capes with customizable designs
- Visible to other players (server-dependent)
- Equip from the "Capes" tab

#### Wings
- Animated wing designs
- Multiple styles: Angel, Demon, Dragon, etc.
- Includes flapping animations

#### Hats
- Headwear cosmetics
- Positioned on player's head
- Various designs from casual to royal

#### Particle Effects
- Surrounding particle effects
- Types: Hearts, Flames, Stars, etc.
- Configurable density and range

#### Emotes
- Animated character actions
- Trigger with keybind or menu
- Types: Wave, Dance, Celebrate, etc.

### Equipping Cosmetics

1. Open the Cosmetics menu
2. Select a cosmetic category
3. Click on a cosmetic to preview
4. Click "Equip" to wear it
5. Equip multiple cosmetics simultaneously

### Cosmetic Profiles

Save your favorite combinations:

1. Equip desired cosmetics
2. Click "Save Profile"
3. Enter a profile name
4. Load profiles quickly from the menu

### Cosmetic Store

Purchase cosmetics with in-game coins:

1. Open "Store" tab in Cosmetics menu
2. Browse available items
3. Click on item to preview
4. Click "Purchase" if you have enough coins

## Performance Settings

Access via Settings → Performance

### Dynamic FPS
- **Enabled**: Reduces FPS when window is unfocused
- **Background FPS**: 10 FPS (configurable)
- **Foreground FPS**: Based on your max FPS setting

### Fast Render
- Optimizes rendering pipeline
- Reduces draw calls
- Implements frustum culling

### Chunk Optimization
- Prioritizes chunk loading near player
- Caches frequently viewed chunks
- Reduces memory usage

### Entity Render Distance
- Default: 64 blocks
- Range: 16-128 blocks
- Lower values improve performance

### Particle Optimization
- Limits max particle count
- Culls distant particles
- Improves FPS in particle-heavy scenes

## Language Settings

### Changing Language

1. Open Settings → Language
2. Select from available languages:
   - 🇺🇸 English
   - 🇹🇷 Türkçe (Turkish)
   - 🇩🇪 Deutsch (German)
   - 🇫🇷 Français (French)
   - 🇪🇸 Español (Spanish)
3. UI updates immediately

### Contributing Translations

See [CONTRIBUTING.md](CONTRIBUTING.md) for translation guidelines.

## Advanced Features

### Version Selector

Switch between Minecraft versions:

1. Click version dropdown in main menu
2. Select desired version
3. Client adapts to version-specific features

Supported versions:
- 1.8.9 (PvP focused)
- 1.12.2 (Modding stable)
- 1.16.5 (Nether Update)
- 1.18.2 (Caves & Cliffs)
- 1.19.4 (Latest features)
- 1.20.x (Current)

### Discord Rich Presence

Shows your game status on Discord:

- Automatically enabled when Discord is running
- Shows current server or "Singleplayer"
- Displays player count on multiplayer

### Screenshot Manager

1. Take screenshot with F2 (default)
2. Access via Settings → Screenshots
3. Features:
   - View all screenshots
   - Sort by date
   - Delete unwanted shots
   - Open screenshots folder

### Configuration Files

Location: `config/minestat_client.json`

Manual editing supported for advanced users:

```json
{
  "language": "en_US",
  "selectedVersion": "1.20.1",
  "video": {
    "vsync": true,
    "renderDistance": 12,
    "maxFps": 120
  },
  "performance": {
    "dynamicFps": true,
    "fastRender": true
  }
}
```

## Troubleshooting

### Client Won't Start

1. Verify Java is installed: `java -version`
2. Check Java version is 8 or higher
3. Run from terminal to see error messages
4. Check logs in `logs/` directory

### Authentication Failed

**Offline Mode**:
- Verify username and password
- Check database file permissions
- Try re-registering

**Premium Mode**:
- Verify internet connection
- Check account credentials
- For Microsoft: Enable pop-ups in browser
- Token may need refresh (logout and login again)

### Performance Issues

1. Lower video settings:
   - Reduce render distance
   - Disable shadows
   - Lower particle density
2. Enable all performance optimizations
3. Close background applications
4. Allocate more RAM to Java

### Cosmetics Not Showing

1. Check "Show Cosmetics" is enabled
2. Verify cosmetic is equipped
3. Some servers may block client cosmetics
4. Restart client if rendering issue persists

### Lost Progress/Settings

- Settings stored in `config/` directory
- User database in `config/users.db`
- Back up these files regularly
- Session data in `config/session.json`

### Build/Compilation Issues

If building from source:
```bash
# Clean build
gradle clean build

# Skip tests if failing
gradle build -x test

# Check for dependency issues
gradle dependencies
```

## Getting Help

- **Issues**: [GitHub Issues](https://github.com/newyearman/MinestatClient/issues)
- **Discussions**: [GitHub Discussions](https://github.com/newyearman/MinestatClient/discussions)
- **Discord**: Join our community server
- **Documentation**: Check README.md and wiki

## Tips & Tricks

1. **Keybinds**: Customize in Settings → Controls
2. **Quick Login**: Enable "Remember Me" for instant access
3. **Profile Switching**: Save multiple cosmetic profiles for different occasions
4. **Performance**: Use Dynamic FPS to save power when AFK
5. **Screenshots**: F2 captures full resolution with all effects

---

Enjoy MinestatClient! If you find this guide helpful, consider starring the project on GitHub.
