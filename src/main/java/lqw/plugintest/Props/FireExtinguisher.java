package lqw.plugintest.Props;

import io.papermc.paper.event.entity.EntityMoveEvent;
import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.HashMap;

public class FireExtinguisher extends Movable implements Listener {
    public FireExtinguisher(){
        blockName = new String(Material.TNT.name());
        entityName = new String(EntityType.PRIMED_TNT.name());
    }

    void entitySpawn(Player player){
        Entity entity = player.getWorld().spawnEntity(player.getLocation().add(player.getLocation().getDirection().multiply(3)) ,EntityType.valueOf(entityName));
        TNTPrimed tntPrimed = (TNTPrimed) entity;
        tntPrimed.setFuseTicks(0x3f3f3f3f);
        controlEn.put(player.getUniqueId(),entity);
        controlEn.get(player.getUniqueId()).setGravity(false);
    }
    void effect(Player player){
        Entity entity = controlEn.get(player.getUniqueId());
        entity.setVelocity(player.getLocation().getDirection());
        entity.addScoreboardTag("fireExtinguisher");
        entity.setGravity(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                Location location = entity.getLocation().add( entity.getVelocity().multiply(1));
                if(location.getBlock().getType() != Material.AIR){
                    ((TNTPrimed) entity).setFuseTicks(0);
                    cancel();
                }
            }
        }.runTaskTimer(PluginTest.pluginTest,0,1);
    }
}
