package lqw.plugintest;

import org.bukkit.plugin.java.JavaPlugin;

public final class PluginTest extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PluginTest loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginTest unloaded");
    }
}
