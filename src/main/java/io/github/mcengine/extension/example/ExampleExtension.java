package io.github.mcengine.extension.example;

import io.github.mcengine.mcextension.api.IMCExtension;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.Executor;

/**
 * A simple example extension for MCEconomy.
 */
public class ExampleExtension implements IMCExtension {

    /**
     * Called when the MCExtensionManager loads this JAR.
     * We use the host plugin's logger to send the message.
     */
    @Override
    public void onLoad(JavaPlugin plugin, Executor executor) {
        // In your system, the ID is managed by the loader, 
        // but for this example, we'll hardcode the ID in the message 
        // as you requested.
        plugin.getLogger().info("[Economy] [ExampleExt] loaded.");
    }

    @Override
    public void onDisable(JavaPlugin plugin, Executor executor) {
        plugin.getLogger().info("[Economy] [ExampleExt] disabled.");
    }
}
