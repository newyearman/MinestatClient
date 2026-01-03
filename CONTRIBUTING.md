# Contributing to MinestatClient

First off, thank you for considering contributing to MinestatClient! It's people like you that make MinestatClient such a great tool.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title** for the issue
* **Describe the exact steps which reproduce the problem**
* **Provide specific examples to demonstrate the steps**
* **Describe the behavior you observed after following the steps**
* **Explain which behavior you expected to see instead and why**
* **Include screenshots and animated GIFs** if possible
* **Include your configuration** (from `config/minestat_client.json`)
* **Include log files** from the `logs/` directory

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a step-by-step description of the suggested enhancement**
* **Provide specific examples to demonstrate the steps**
* **Describe the current behavior and expected behavior**
* **Explain why this enhancement would be useful**
* **List some other clients or applications where this enhancement exists**

### Pull Requests

* Fill in the required template
* Do not include issue numbers in the PR title
* Follow the Java style guide
* Include thoughtfully-worded, well-structured tests
* Document new code
* End all files with a newline

## Development Setup

### Prerequisites

* Java 8 or higher
* Gradle 7.0 or higher
* Git

### Setup

```bash
# Fork and clone the repository
git clone https://github.com/YOUR_USERNAME/MinestatClient.git
cd MinestatClient

# Build the project
gradle build

# Run tests
gradle test
```

## Style Guides

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line

### Java Style Guide

* Use 4 spaces for indentation (no tabs)
* Use camelCase for variables and methods
* Use PascalCase for classes
* Use UPPER_SNAKE_CASE for constants
* Add JavaDoc comments for all public methods and classes
* Keep lines under 120 characters when possible
* Use meaningful variable names

Example:
```java
/**
 * Authenticates a user with the given credentials.
 * 
 * @param username The username to authenticate
 * @param password The password to authenticate
 * @return AuthenticationResult containing the result of authentication
 */
public AuthenticationResult authenticate(String username, String password) {
    // Implementation
}
```

### Documentation Style Guide

* Use Markdown for documentation
* Reference methods and classes using backticks: `ClassName.methodName()`
* Include code examples where appropriate
* Keep explanations concise but complete

## Project Structure

When adding new features, please follow the existing project structure:

* `auth/` - Authentication related code
* `cosmetics/` - Cosmetics system
* `optimization/` - Performance optimization
* `ui/` - User interface components
* `config/` - Configuration management
* `version/` - Version management
* `utils/` - Utility classes

## Testing

* Write tests for all new features
* Ensure all tests pass before submitting PR
* Aim for high test coverage
* Include both unit and integration tests

```bash
# Run all tests
gradle test

# Run specific test class
gradle test --tests "com.minestat.client.auth.AuthenticationManagerTest"
```

## Adding Translations

To add a new language:

1. Create a new JSON file in `src/main/resources/lang/` (e.g., `ja_JP.json` for Japanese)
2. Copy the structure from `en_US.json`
3. Translate all strings
4. Register the language in `I18n.java`
5. Test the translations in-game

## Adding New Cosmetics

1. Create texture files in `src/main/resources/assets/cosmetics/`
2. Add cosmetic definition in `CosmeticManager.loadDefaultCosmetics()`
3. Implement rendering logic in `CosmeticRenderer`
4. Add translations for cosmetic names
5. Test rendering in-game

## Adding New Optimizations

1. Create optimizer class in `optimization/` package
2. Implement enable/disable/update methods
3. Integrate with `OptimizationManager`
4. Add configuration options to `ConfigManager`
5. Add UI controls in `SettingsScreen`
6. Test performance impact

## Questions?

Feel free to ask questions in:
* GitHub Discussions
* Discord server
* Issue comments

## Recognition

Contributors will be recognized in:
* README.md contributors section
* Release notes
* In-game credits

Thank you for contributing to MinestatClient! 🎉
