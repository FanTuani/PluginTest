package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class FireExtinguisher extends Movable implements Listener {
    public FireExtinguisher() {
        blockName = Material.TNT.name();
        entityName = EntityType.PRIMED_TNT.name();
    }

    void entitySpawn(Player player) {
        Entity entity =
                player.getWorld().spawnEntity(player.getLocation().add(player.getLocation().getDirection().multiply(3)), EntityType.valueOf(entityName));
        TNTPrimed tntPrimed = (TNTPrimed) entity;
        tntPrimed.setFuseTicks(0x3f3f3f3f);
        controlEn.put(player.getUniqueId(), entity);
        controlEn.get(player.getUniqueId()).setGravity(false);
    }

    void effect(Player player) {
        TNTPrimed tnt = (TNTPrimed) controlEn.get(player.getUniqueId());
        tnt.setVelocity(player.getLocation().getDirection());
        tnt.addScoreboardTag("fireExtinguisher");
        tnt.setGravity(true);

        Vector direction = player.getLocation().getDirection();
        tnt.setVelocity(direction.multiply(1.8));
        new BukkitRunnable() {
            @Override
            public void run() {
                Vector vel = tnt.getVelocity();
                if (vel.getX() == 0 || vel.getY() == 0 || vel.getZ() == 0) {
                    tnt.setFuseTicks(0);
                    cancel();
                }
                if (!tnt.getNearbyEntities(0.5, 0.5, 0.5).isEmpty()) {
                    for (Entity entity : tnt.getNearbyEntities(0.5, 0.5, 0.5)) {
                        if (entity.getType().name().equals(EntityType.PRIMED_TNT.name())) {
                            entity.getWorld().strikeLightning(entity.getLocation());
                            break;
                        }
                    }
                    tnt.setFuseTicks(0);
                    cancel();
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }
}
