package lqw.plugintest.MapInteractions;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class JumpPad implements Listener {
    public final PluginTest plugin;

    public JumpPad(PluginTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerStepOnGoldenPressurePlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock() == null) return;
            if (event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.1f, 2);
                Vector vec = player.getLocation().getDirection();
                vec.setY(1.6);
                player.setVelocity(vec);
            }
        }
    }
}
