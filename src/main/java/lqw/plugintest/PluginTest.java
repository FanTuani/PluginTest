package lqw.plugintest;

import lqw.plugintest.BasicRules.SpawnSupply;
import lqw.plugintest.BasicRules.Welcome;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginTest extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PluginTest loaded");
        registerEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginTest unloaded");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new Welcome(), this);
        getServer().getPluginManager().registerEvents(new SpawnSupply(), this);
    }
}
