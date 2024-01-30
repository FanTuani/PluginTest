package lqw.plugintest.MapInteractions;

import lqw.plugintest.PluginTest;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chain;
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
        if (event.getPlayer().isFlying()) return;
        if (event.getClickedBlock().getType() != Material.CHAIN) return;
        Player player = event.getPlayer();
        Block chain = event.getClickedBlock();

        Location ballLoc = chain.getLocation();
        Chain chainData = (Chain) chain.getBlockData();
        Axis axis = chainData.getAxis();
        if (axis != Axis.Y) {
            ballLoc.setY(ballLoc.getY() - 2);
            ballLoc.setX(ballLoc.getX() + 0.5);
            ballLoc.setZ(ballLoc.getZ() + 0.5);
        }

        Entity ball = ballLoc.getWorld().spawnEntity(ballLoc, EntityType.SNOWBALL);
        ball.addPassenger(player);
        ball.setGravity(false);

        if (axis == Axis.Y) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Location location = ball.getLocation();
                    ball.setVelocity(new Vector(0, 0.4, 0));
                    if (location.getBlock().getType() != Material.CHAIN || ball.getPassengers().isEmpty()) {
                        ball.remove();
                        player.setVelocity(player.getLocation().getDirection().multiply(0.5));
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        } else if (axis == Axis.X) {
            Vector vec = player.getLocation().getDirection();
            vec.setY(0);
            vec.setZ(0);
            vec.normalize();
            vec.multiply(0.5);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ball.setVelocity(vec);
                    Location location = ball.getLocation();
                    location.setY(location.getY() + 2);
                    location.setX(location.getX() + vec.getX() * 2);
                    if (location.getBlock().getType() != Material.CHAIN || ball.getPassengers().isEmpty()) {
                        ball.remove();
                        player.setVelocity(player.getLocation().getDirection().multiply(0.5));
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        } else {
            Vector vec = player.getLocation().getDirection();
            vec.setY(0);
            vec.setX(0);
            vec.normalize();
            vec.multiply(0.5);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ball.setVelocity(vec);
                    Location location = ball.getLocation();
                    location.setY(location.getY() + 2);
                    location.setZ(location.getZ() + vec.getZ() * 2);
                    if (location.getBlock().getType() != Material.CHAIN || ball.getPassengers().isEmpty()) {
                        ball.remove();
                        player.setVelocity(player.getLocation().getDirection().multiply(0.5));
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }
}
