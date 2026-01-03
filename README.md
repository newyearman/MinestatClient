# MinestatClient

<div align="center">

![MinestatClient Logo](https://via.placeholder.com/200x200?text=MinestatClient)

**A Professional, Full-Featured Minecraft Client**

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/java-8+-orange.svg)](https://www.java.com)
[![Gradle](https://img.shields.io/badge/gradle-7.0+-green.svg)](https://gradle.org)

</div>

## 📋 Overview

MinestatClient is a professional, feature-rich Minecraft client built from the ground up with modern Java. It offers multi-version support, dual authentication (offline and premium), an extensive cosmetics system, performance optimizations, and full internationalization.

## ✨ Features

### 🔐 Dual Authentication System
- **Offline/Crack Mode**: Local user registration with SQLite database, session management, and "Remember Me" functionality
- **Premium Mode**: Microsoft OAuth 2.0 and legacy Mojang account support with automatic token refresh

### 🎨 Comprehensive Cosmetics System
- **Cosmetic Types**: Capes, Wings, Hats, Particle Effects, and Emotes
- **Cosmetic Management**: Inventory system, preview functionality, and in-game store
- **Rendering**: OpenGL-based 3D rendering with animation support
- **Profiles**: Save and load multiple cosmetic configurations

### ⚡ Performance Optimization
- **Dynamic FPS**: Automatically reduces FPS when window is unfocused
- **Fast Render**: Optimized rendering pipeline with batching and culling
- **Chunk Optimization**: Intelligent chunk loading and caching
- **Entity Render Distance**: Configurable entity rendering with LOD support
- **Particle Optimization**: Particle density control and distance culling

### 🌍 Multi-Language Support
Fully translated interface supporting:
- 🇺🇸 English (en_US)
- 🇹🇷 Turkish (tr_TR)
- 🇩🇪 German (de_DE)
- 🇫🇷 French (fr_FR)
- 🇪🇸 Spanish (es_ES)

### 🎮 Multi-Version Support
Compatible with:
- Minecraft 1.8.9
- Minecraft 1.12.2
- Minecraft 1.16.5
- Minecraft 1.18.2
- Minecraft 1.19.4
- Minecraft 1.20.x

### 🎯 Additional Features
- **Discord Rich Presence**: Show your game status on Discord
- **Screenshot Manager**: Organized screenshot management with date sorting
- **Modern UI**: Sleek, animated interface with intuitive navigation
- **Config System**: JSON-based configuration with hot-reload support
- **Logging**: Comprehensive Log4j-based logging system

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Gradle 7.0 or higher (or use included Gradle wrapper)

### Building from Source

```bash
# Clone the repository
git clone https://github.com/newyearman/MinestatClient.git
cd MinestatClient

# Build with Gradle wrapper (recommended)
./gradlew build

# Or use system Gradle
gradle build
```

## Building

### Build Fat JAR (with all dependencies):
```bash
./gradlew shadowJar
```

Output: `build/libs/MinestatClient-1.0.0-all.jar`

The fat JAR includes all runtime dependencies (log4j, gson, sqlite-jdbc, okhttp) and can be run standalone:
```bash
java -jar build/libs/MinestatClient-1.0.0-all.jar
```

### Create Windows EXE:

#### Prerequisites
1. **Java 8 or higher** - Make sure Java is installed and JAVA_HOME is set
2. **Launch4j** - Download from https://launch4j.sourceforge.net/

#### Step-by-Step Instructions

1. **Build the fat JAR first:**
   ```bash
   ./gradlew shadowJar
   ```
   This creates `build/libs/MinestatClient-1.0.0-all.jar`

2. **Using Launch4j GUI:**
   - Download and install Launch4j from https://launch4j.sourceforge.net/
   - Open Launch4j
   - Load the provided configuration: `File > Load config` → select `launch4j.xml`
   - Click the "Build wrapper" button (gear icon)
   - The `MinestatClient.exe` will be created in the project root

3. **Using Launch4j Command Line:**
   ```bash
   # Windows
   launch4jc.exe launch4j.xml
   
   # Linux/Mac (with Wine)
   launch4j launch4j.xml
   ```

4. **Run the EXE:**
   - Double-click `MinestatClient.exe` to launch
   - The application will use your system's Java installation
   - Minimum Java 8 required

#### Configuration Details

The `launch4j.xml` file includes:
- **Main class:** `com.minestat.client.MinestatClient`
- **Min JRE version:** `1.8.0`
- **JAR path:** `build/libs/MinestatClient-1.0.0-all.jar`
- **Icon:** `src/main/resources/icon.ico`
- **GUI application** (no console window)
- **Heap size:** 128MB initial, 1GB max

#### Troubleshooting

**"Java not found" error:**
- Ensure Java 8+ is installed: `java -version`
- Set JAVA_HOME environment variable
- Make sure Java is in your PATH

**"JAR not found" error:**
- Run `./gradlew shadowJar` to build the JAR first
- Verify `build/libs/MinestatClient-1.0.0-all.jar` exists

**EXE won't start:**
- Right-click the EXE → Properties → Compatibility
- Try "Run as administrator"
- Check Windows Event Viewer for error details

**Out of memory errors:**
- Edit `launch4j.xml` and increase `<maxHeapSize>1024</maxHeapSize>`
- Rebuild the EXE with Launch4j

### Other Build Options

```bash
# Build regular JAR (without dependencies)
./gradlew jar

# Build sources JAR
./gradlew sourcesJar

# Build javadoc JAR
./gradlew javadocJar

# Clean build directory
./gradlew clean
```

**Note**: The shadowJar task produces a fat JAR that includes all dependencies. For development or deployment in Minecraft modding environments (Forge/Fabric), use the regular `jar` task instead, as the modding platform will provide the required dependencies.

## 📁 Project Structure

```
MinestatClient/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/minestat/client/
│   │   │       ├── MinestatClient.java      # Main client class
│   │   │       ├── auth/                    # Authentication system
│   │   │       │   ├── AuthenticationManager.java
│   │   │       │   ├── OfflineAuthProvider.java
│   │   │       │   ├── PremiumAuthProvider.java
│   │   │       │   ├── SessionManager.java
│   │   │       │   ├── UserProfile.java
│   │   │       │   └── AuthenticationResult.java
│   │   │       ├── cosmetics/               # Cosmetics system
│   │   │       │   ├── CosmeticManager.java
│   │   │       │   ├── Cosmetic.java
│   │   │       │   ├── CosmeticType.java
│   │   │       │   ├── CosmeticInventory.java
│   │   │       │   ├── CosmeticProfile.java
│   │   │       │   ├── CosmeticRenderer.java
│   │   │       │   └── CosmeticStore.java
│   │   │       ├── optimization/            # Performance optimization
│   │   │       │   ├── OptimizationManager.java
│   │   │       │   ├── DynamicFPSController.java
│   │   │       │   ├── FastRenderOptimizer.java
│   │   │       │   ├── ChunkOptimizer.java
│   │   │       │   ├── EntityRenderOptimizer.java
│   │   │       │   └── ParticleOptimizer.java
│   │   │       ├── ui/                      # User interface
│   │   │       │   ├── UIManager.java
│   │   │       │   ├── Screen.java
│   │   │       │   ├── LoginScreen.java
│   │   │       │   ├── MainMenuScreen.java
│   │   │       │   └── SettingsScreen.java
│   │   │       ├── config/                  # Configuration management
│   │   │       │   └── ConfigManager.java
│   │   │       ├── version/                 # Version management
│   │   │       │   ├── VersionManager.java
│   │   │       │   ├── MinecraftVersion.java
│   │   │       │   └── VersionType.java
│   │   │       └── utils/                   # Utility classes
│   │   │           ├── I18n.java
│   │   │           ├── DiscordRPC.java
│   │   │           └── ScreenshotManager.java
│   │   └── resources/
│   │       ├── assets/
│   │       │   ├── cosmetics/              # Cosmetic textures
│   │       │   └── ui/                     # UI assets
│   │       ├── lang/                       # Language files
│   │       │   ├── en_US.json
│   │       │   ├── tr_TR.json
│   │       │   ├── de_DE.json
│   │       │   ├── fr_FR.json
│   │       │   └── es_ES.json
│   │       └── minestat.mixins.json       # Mixin configuration
├── build.gradle                           # Build configuration
├── settings.gradle                        # Project settings
├── .gitignore                            # Git ignore rules
├── README.md                             # This file
├── CONTRIBUTING.md                       # Contribution guidelines
└── LICENSE                               # License file
```

## ⚙️ Configuration

The client configuration is stored in `config/minestat_client.json` and includes:

```json
{
  "language": "en_US",
  "selectedVersion": "1.20.1",
  "rememberMe": false,
  "video": {
    "vsync": true,
    "renderDistance": 12,
    "shadows": true,
    "maxFps": 120
  },
  "performance": {
    "dynamicFps": true,
    "fastRender": true,
    "chunkOptimization": true
  }
}
```

## 🎮 Usage

### Login
1. Launch the client
2. Choose authentication mode (Offline or Premium)
3. Enter credentials or use Microsoft login
4. Enable "Remember Me" for automatic login

### Cosmetics
1. Navigate to the Cosmetics menu
2. Browse available cosmetics in your inventory
3. Preview cosmetics before equipping
4. Equip multiple cosmetics simultaneously
5. Save favorite combinations as profiles

### Settings
Access comprehensive settings for:
- Video quality and performance
- Language preferences
- Cosmetic configurations
- Account management
- Performance optimizations

## 🔧 Development

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Keep methods focused and concise

### Adding New Features
1. Create feature branch from `main`
2. Implement feature with tests
3. Update documentation
4. Submit pull request

### Testing
```bash
# Run tests
gradle test

# Run with coverage
gradle test jacocoTestReport
```

## 📝 API Documentation

### Authentication API
```java
// Authenticate offline
AuthenticationResult result = MinestatClient.getInstance()
    .getAuthManager()
    .authenticateOffline("username", "password", true);

// Authenticate with Microsoft
AuthenticationResult result = MinestatClient.getInstance()
    .getAuthManager()
    .authenticateMicrosoft(true);
```

### Cosmetics API
```java
// Equip a cosmetic
MinestatClient.getInstance()
    .getCosmeticManager()
    .equipCosmetic("angel_wings", CosmeticType.WINGS);

// Save cosmetic profile
MinestatClient.getInstance()
    .getCosmeticManager()
    .saveProfile("MyFavorite");
```

### Optimization API
```java
// Set entity render distance
MinestatClient.getInstance()
    .getOptimizationManager()
    .setEntityRenderDistance(64);

// Get current FPS
int fps = MinestatClient.getInstance()
    .getOptimizationManager()
    .getCurrentFPS();
```

## 🤝 Contributing

We welcome contributions! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Minecraft and Mojang for the amazing game
- The Fabric and Forge teams for their modding APIs
- The open-source community for various libraries used

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/newyearman/MinestatClient/issues)
- **Discussions**: [GitHub Discussions](https://github.com/newyearman/MinestatClient/discussions)
- **Discord**: [Join our Discord](https://discord.gg/minestatclient)

## 🗺️ Roadmap

- [ ] Friend system implementation
- [ ] Waypoints and minimap
- [ ] More cosmetic types
- [ ] Marketplace for cosmetics
- [ ] Plugin API
- [ ] More language support
- [ ] Mobile companion app

---

<div align="center">
Made with ❤️ by the MinestatClient Team
</div>