package lqw.plugintest.Props;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ADS implements Listener {
    @EventHandler
    public void onTargetBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.ENCHANTING_TABLE) return;
        Location adsLoc = event.getBlock().getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (adsLoc.getBlock().getType() != Material.ENCHANTING_TABLE) cancel();
                for (Player target : adsLoc.getNearbyPlayers(50, 50, 50)) {
                    Location targetLoc = target.getEyeLocation().clone();

                    Location snowBallSpawnLoc = adsLoc.clone().add(new Vector(0.5, 0, 0.5));
                    snowBallSpawnLoc.getWorld().playSound(snowBallSpawnLoc, Sound.BLOCK_ANVIL_BREAK, 1.5f, 1.5f);
                    snowBallSpawnLoc.getWorld().playSound(snowBallSpawnLoc, Sound.BLOCK_ANVIL_BREAK, 0.6f, 0.5f);
                    snowBallSpawnLoc.getWorld().playSound(snowBallSpawnLoc, Sound.BLOCK_SOUL_SAND_FALL, 0.6f, 0.5f);
                    snowBallSpawnLoc.getWorld().playSound(snowBallSpawnLoc, Sound.BLOCK_BONE_BLOCK_STEP, 0.6f, 0.5f);
                    snowBallSpawnLoc.getWorld().playSound(snowBallSpawnLoc, Sound.BLOCK_NOTE_BLOCK_BASS, 0.7f, 0.1f);
                    target.sendActionBar(ChatColor.RED + "|ADS LOCKED|");
                    double dis = snowBallSpawnLoc.distance(targetLoc);
                    Location predictLoc =
                            targetLoc.clone().add(LQW.getPlayerVelocity(target).clone().multiply(dis / 2));
                    Vector vector = predictLoc.subtract(snowBallSpawnLoc).toVector();
                    snowBallSpawnLoc.add(vector.normalize());

                    Arrow arrow = adsLoc.getWorld().spawn(snowBallSpawnLoc, Arrow.class);
                    arrow.setGravity(false);

                    adsLoc.getWorld().spawnParticle(Particle.LAVA, arrow.getLocation(), 5);

                    new BukkitRunnable() {
                        int ticks = 0;

                        @Override
                        public void run() {
                            if (ticks == 100)
                                arrow.remove();
                            arrow.setVelocity(vector.normalize().multiply(2));
                            ticks++;
                        }
                    }.runTaskTimer(PluginTest.pluginTest, 0, 1);
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 5, 1);
    }

//    @EventHandler
//    public void onPlayerHitByADS(ProjectileHitEvent event) {
//        if (!(event.getHitEntity() instanceof Player) || !(event.getEntity() instanceof Snowball)) return;
//        Player player = (Player) event.getHitEntity();
//        player.damage(1);
//        player.playSound(player.getLocation(), Sound.BLOCK_METAL_HIT, 0.5f, 1);
//        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getEyeLocation(), 5, 0, 0, 0, 1);
//        player.getWorld().spawnParticle(Particle.CRIT, player.getEyeLocation(), 5, 0, 0, 0, 1);
//    }
}
