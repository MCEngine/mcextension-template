# MCExtension Example

This repository is a minimal example project showing how to build an external extension that implements the `io.github.mcengine.mcextension.api.IMCExtension` API.

## What this is

- An example extension JAR that can be loaded by a host plugin using **MCExtension**.
- The extension entrypoint is defined in `src/main/resources/extension.yml`.

## Requirements

- Java 17+
- Gradle (the wrapper is included)

## Dependency

This project targets:

```xml
<dependency>
  <groupId>io.github.mcengine</groupId>
  <artifactId>mcextension</artifactId>
  <version>2026.0.3-1-SNAPSHOT</version>
</dependency>
```

And the **PaperMC API**:

```xml
<dependency>
  <groupId>io.papermc.paper</groupId>
  <artifactId>paper-api</artifactId>
  <version>1.21.1-R0.1-SNAPSHOT</version>
</dependency>
```

## Project structure

- `src/main/java/.../ExampleExtension.java`
  - Implements `IMCExtension`
  - Provides lifecycle hooks:
    - `onLoad(JavaPlugin plugin, Executor executor)`
    - `onDisable(JavaPlugin plugin, Executor executor)`
- `src/main/resources/extension.yml`
  - Declares the extension `name`, `version`, and `main` class

## extension.yml configuration

The `extension.yml` file defines how your extension is loaded:

```yaml
# The unique identifier for this extension
name: ExampleExt
# The version of this extension
version: 1.0.0
# The full package path to your main class
main: io.github.mcengine.extension.example.ExampleExtension

# Optional: Ensure MCEconomy is loaded first
base:
  depend: [MCEconomy]
```

### Configuration options

- **name**: Unique identifier for your extension (required)
- **version**: Extension version string (required)  
- **main**: Fully qualified class name of your main extension class (required)
- **base.depend**: List of extensions that must be loaded before this one (optional)

## Build

Run:

```powershell
.\gradlew.bat clean build
```

The JAR will be created under:

- `build/libs/`

## Using the extension

- Copy the built JAR into the location your host plugin scans for extensions.
- Ensure the host plugin is installed and started before the extension loads.
- On server start/stop you should see log messages emitted by `ExampleExtension`.
