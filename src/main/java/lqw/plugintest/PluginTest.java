package lqw.plugintest;

import lqw.plugintest.BasicRules.BasicCancellers;
import lqw.plugintest.BasicRules.BreathToHeal;
import lqw.plugintest.BasicRules.SpawnSupply;
import lqw.plugintest.BasicRules.Welcome;
import lqw.plugintest.MapInteractions.ChainElevator;
import lqw.plugintest.MapInteractions.JumpPad;
import lqw.plugintest.Props.*;
import lqw.plugintest.Props.BatKing.BatSpawner;
import lqw.plugintest.Props.FireCircle.FireCircle;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class PluginTest extends JavaPlugin {
    public static PluginTest pluginTest;

    @Override
    public void onEnable() {
        getLogger().info("PluginTest loaded");
        for (Player player : getServer().getOnlinePlayers()) { //reload info
            player.sendTitle(ChatColor.GREEN + "Reloaded!", "", 5, 10, 5);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f);
            LQW.lastVecLoc.put(player, player.getLocation().toVector());
            LQW.nowVelocity.put(player, new Vector(0, 0, 0));
        }
        registerEvents();
        PluginTest.pluginTest = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginTest unloaded");
        for (Player player : getServer().getOnlinePlayers()) { //reload info
            player.sendTitle(ChatColor.YELLOW + "Reloading plugins...", "", 5, 10, 5);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new Welcome(), this);
        getServer().getPluginManager().registerEvents(new SpawnSupply(this), this);
        getServer().getPluginManager().registerEvents(new BasicCancellers(), this);
        getServer().getPluginManager().registerEvents(new ChainElevator(this), this);
        getServer().getPluginManager().registerEvents(new BreathToHeal(this), this);
        getServer().getPluginManager().registerEvents(new JumpPad(this), this);
        getServer().getPluginManager().registerEvents(new Landmine(), this);
        getServer().getPluginManager().registerEvents(new FireExtinguisher(), this);
        getServer().getPluginManager().registerEvents(new CashBox(), this);
        getServer().getPluginManager().registerEvents(new Hook(), this);
        getServer().getPluginManager().registerEvents(new Flash(), this);
        getServer().getPluginManager().registerEvents(new PhaseRift(), this);
        getServer().getPluginManager().registerEvents(new ADS(), this);
        getServer().getPluginManager().registerEvents(new LQW(), this);
        getServer().getPluginManager().registerEvents(new Missile(), this);
        getServer().getPluginManager().registerEvents(new DecoyMissile(), this);
        getServer().getPluginManager().registerEvents(new MeteorStrike(), this);
        getServer().getPluginManager().registerEvents(new BatSpawner(), this);
        getServer().getPluginManager().registerEvents(new FireCircle(), this);
    }
}
