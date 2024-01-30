package lqw.plugintest.MapInteractions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChainElevator implements Listener {
    @EventHandler
    public void onPlayerRightClickChainBlock(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CHAIN) return;
        Player player = event.getPlayer();
        Block chain = event.getClickedBlock();
        player.setHealth(5);
    }
}
