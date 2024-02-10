package lqw.plugintest.Props;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ADS implements Listener {
    @EventHandler
    public void onTargetBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.TARGET) return;
        Location adsLoc = event.getBlock().getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (adsLoc.getBlock().getType() != Material.TARGET) cancel();
                for (Player target : adsLoc.getNearbyPlayers(50, 50, 50)) {
                    Location targetLoc = target.getEyeLocation().clone();
                    double dis = adsLoc.distance(targetLoc);

                    Location predictLoc = targetLoc.clone().add(LQW.getPlayerVelocity(target).clone().multiply(dis));

                    predictLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, predictLoc, 10, 0, 0, 0, 0);
                    Vector vector = predictLoc.subtract(adsLoc.clone().add(new Vector(0.5, 1, 0.5))).toVector();

                    Snowball snowball = adsLoc.getWorld().spawn(adsLoc.clone().add(new Vector(0.5, 1, 0.5)),
                            Snowball.class);
                    snowball.setGravity(false);
                    snowball.setVelocity(vector.normalize());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            snowball.remove();
                        }
                    }.runTaskLater(PluginTest.pluginTest, 100);
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 5, 1);
    }
}
