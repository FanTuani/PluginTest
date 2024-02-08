package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Hook implements Listener {
    @EventHandler
    public void onUsingHook(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer();
        ItemStack itemOnHand = player.getInventory().getItemInMainHand();
        if (itemOnHand.getType() != Material.IRON_PICKAXE) return;

        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setGravity(false);
        arrow.setVelocity(player.getLocation().getDirection().multiply(3));
        arrow.addScoreboardTag("hook");
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 0.8f, 2);
    }

    @EventHandler
    public void onShot(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) event.getEntity();
        if (!(arrow.getShooter() instanceof Player)) return;
        if (!arrow.getScoreboardTags().contains("hook")) return;
        Player player = (Player) arrow.getShooter();
        player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_FALL, 1, 1);
        Location targetLoc = arrow.getLocation();
        if (targetLoc.distance(player.getLocation()) > 30) return;
        player.setGravity(false);
        player.getVelocity().add(new Vector(0, 1, 0));
        arrow.remove();

        targetLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, targetLoc, 10, 0, 0, 0, 0.05);

        new BukkitRunnable() {
            int ticks = 0;

            private void cancelRun(Player player) {
                cancel();
                player.setGravity(true);
                player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_FALL, 1, 1);
            }

            @Override
            public void run() {
                if (ticks > 10) {
                    player.setGravity(true);
                }
                if (player.getLocation().distance(targetLoc) < 1 || player.getLocation().distance(targetLoc) > 30) {
                    cancelRun(player);
                }
                if (player.isSneaking()) {
                    cancelRun(player);
                }
                double speed = player.getVelocity().length();
                if (speed < 0.25f && ticks > 20) {
                    cancelRun(player);
                }
                Vector vel = arrow.getLocation().subtract(player.getLocation()).toVector();
                vel.setY(vel.getY() + 1);
                double dis = Math.max(player.getVelocity().length(),
                        arrow.getLocation().distance(player.getLocation()));
                vel = vel.multiply(Math.log(dis) / 160);
                player.setVelocity(player.getVelocity().add(vel));
                targetLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, targetLoc, 3, 0, 0, 0, 0.04);
                ticks++;
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }
}
