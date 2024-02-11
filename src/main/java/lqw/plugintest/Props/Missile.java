package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class Missile implements Listener {
    @EventHandler
    public void whenMissileLaunched(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if (inventory.getItemInMainHand().getType() != Material.COMPASS) return;

        Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection());
        arrow.setGravity(false);
        arrow.setGlowing(true);
        arrow.setCustomName("Missile");
        arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 1.5f);
        player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 1, 1);
        player.playSound(player.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1, 1);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                arrow.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, arrow.getLocation(), 1, 0, 0, 0, 0);
                if (ticks >= 10) {
                    List<Entity> list = arrow.getNearbyEntities(50, 50, 50);
                    double nearestDistance = 500000;
                    LivingEntity nearestTarget = null;
                    for (Entity target : list) {
                        if (!(target instanceof LivingEntity) || target == player) continue;
                        if (arrow.getLocation().distance(target.getLocation()) < nearestDistance) {
                            nearestDistance = arrow.getLocation().distance(target.getLocation());
                            nearestTarget = (LivingEntity) target;
                        }
                    }
                    if (nearestTarget != null) {
                        arrow.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, arrow.getLocation(), 10, 0, 0, 0, 0.1);
                        arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation(), 10, 0, 0, 0, 0.1);
                        Vector vector = nearestTarget.getEyeLocation().subtract(arrow.getLocation()).toVector();
                        arrow.setVelocity(vector.normalize().multiply(2));
                    }
                    if (!arrow.isValid() || arrow.isInBlock()) { // hit
                        arrow.getWorld().createExplosion(arrow.getLocation(), 4);

                        arrow.getWorld().spawnParticle(Particle.CRIT_MAGIC, arrow.getLocation(), 100, 2, 2, 2, 1);
                        arrow.getWorld().spawnParticle(Particle.CRIT, arrow.getLocation(), 100, 2, 2, 2, 1);

                        arrow.remove();
                        cancel();
                    }
                }
                if (ticks >= 100) {
                    arrow.remove();
                    cancel();
                }
                ticks++;
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }

//    @EventHandler
//    public void onPlayerHitByMissile(ProjectileHitEvent event) {
//        if (!(event.getHitEntity() instanceof Player) || !(event.getEntity() instanceof Arrow)) return;
//        Arrow arrow = (Arrow) event.getEntity();
//        if (!arrow.getName().equals("Missile")) return;
//        arrow.remove();
//
//        Player player = (Player) event.getHitEntity();
//        player.damage(1);
//        player.playSound(player.getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 2);
//        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getEyeLocation(), 100, 1, 1, 1, 1);
//        player.getWorld().spawnParticle(Particle.CRIT, player.getEyeLocation(), 100, 1, 1, 1, 1);
//    }
}
