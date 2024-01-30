package lqw.plugintest.MapInteractions;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import javax.swing.*;

public class JumpPad implements Listener {
    public final PluginTest plugin;

    public JumpPad(PluginTest plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerStepOnGoldenPressurePlate(PlayerInteractEvent step){
        if(step.getAction() == Action.PHYSICAL) {
            if(step.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE){
                Player player = step.getPlayer();
                Vector nowv = player.getVelocity();
                nowv.setY(1.8);
                player.setVelocity(nowv);
            }
        }
    }
}
