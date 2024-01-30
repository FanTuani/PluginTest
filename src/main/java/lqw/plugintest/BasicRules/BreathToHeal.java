package lqw.plugintest.BasicRules;

import lqw.plugintest.PluginTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BreathToHeal implements Listener {
    private final PluginTest plugin;
    private HashMap<Player, Integer> healthRecoveryTasks = new HashMap<>();

    public BreathToHeal(PluginTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (healthRecoveryTasks.containsKey(player)) {
                int taskId = healthRecoveryTasks.get(player);
                Bukkit.getScheduler().cancelTask(taskId);
            }

            int taskId = new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.getHealth() == player.getMaxHealth()) {
                        healthRecoveryTasks.remove(player);
                        cancel();
                    }
                    player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                }
            }.runTaskTimer(plugin, 60, 10).getTaskId();
            healthRecoveryTasks.put(player, taskId);
        }
    }
}
