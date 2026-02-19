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
  <version>2026.0.3-6</version>
</dependency>
```

And the **PaperMC API**:

```xml
<dependency>
  <groupId>io.papermc.paper</groupId>
  <artifactId>paper-api</artifactId>
  <version>1.21.11-R0.1-SNAPSHOT</version>
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
name: "MyExtension"
main: "io.github.mcengine.MyExtension"
version: "1.0.0"

extension:
  depend: [OtherExtID]     # optional: other extensions required
git:
  provider: github         # or gitlab
  owner: my-org            # repo owner/group
  repository: my-repo      # repo name
  # token omitted: resolved from env (USER_GITHUB_TOKEN / USER_GITLAB_TOKEN) or host plugin config git.token
```

### Configuration options

- **name**: Unique identifier for your extension (required)
- **main**: Fully qualified class name of your main extension class (required)
- **version**: Extension version string (required)
- **extension.depend**: List of extensions that must be loaded before this one (optional)
- **git.provider/owner/repository**: Git hosting details for publishing (optional; tokens resolved from env/host config)

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
