package lqw.plugintest.MapInteractions;

import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ChainElevator implements Listener {
    private final PluginTest plugin;

    public ChainElevator(PluginTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRightClickChainBlock(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CHAIN) return;
        Player player = event.getPlayer();
        Block chain = event.getClickedBlock();

        Location minecartLoc = chain.getLocation();
//        minecartLoc.add(new Vector(0.5, 0, 0.5));
        Entity vehicle = minecartLoc.getWorld().spawnEntity(minecartLoc, EntityType.SNOWBALL);
        vehicle.addPassenger(player);
        vehicle.setGravity(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location location = vehicle.getLocation();
                vehicle.setVelocity(new Vector(0, 0.4, 0));
                if (location.getBlock().getType() != Material.CHAIN || vehicle.getPassengers().isEmpty()) {
                    vehicle.remove();
                    player.setVelocity(player.getLocation().getDirection().multiply(0.5));
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
