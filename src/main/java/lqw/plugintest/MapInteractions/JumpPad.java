package lqw.plugintest.MapInteractions;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
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
            if (event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                Player player = event.getPlayer();
                Vector vec = player.getLocation().getDirection();
                vec.setY(1.6);
                player.setVelocity(vec);
            }
        }
    }
}
