package lqw.plugintest.Props.FireCircle;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireCircle implements Listener {
    public double speed = 1, radius = 1, circleSpeed = 0.1;
    @EventHandler
    void useFirer(PlayerInteractEvent event){
        if(LQW.isNotUsing(event, Material.BLAZE_ROD.name()))return;
        Vector loc, subLoc[] = new Vector[10], dir;
        Player player = event.getPlayer();
        World world = player.getWorld();
        dir = player.getLocation().getDirection().normalize();
        loc = player.getEyeLocation().toVector();
        subLoc[0] = dir.getCrossProduct(new Vector(1, 0, 0)).normalize().multiply(radius);
        for(int i = 1; i < 10; i ++){
            subLoc[i] = subLoc[i - 1].clone();
            subLoc[i].rotateAroundNonUnitAxis(dir, 360/10);
        }
        new BukkitRunnable(){
            int timer = 0;
            @Override
            public void run() {
                if(timer++ > 100)cancel();
                loc.add(dir.clone().multiply(speed));
                for(Entity e : world.getNearbyEntities(loc.toLocation(world), 1, 1, 1)){
                    if(e.getUniqueId() == player.getUniqueId())continue;
//                    if(e.getLocation().toVector().clone().subtract(loc).length() <= radius * 2 && e instanceof LivingEntity){
//                        ((LivingEntity)e).damage(1, player);
//                    }
//                    player.sendMessage(e.getName());
                    for(Vector v : subLoc){
                        if(e instanceof LivingEntity && e.getBoundingBox().contains(loc.clone().add(v))){
                            ((LivingEntity)e).damage(1, player);
                        }
                    }
                    if(e instanceof LivingEntity && e.getBoundingBox().getCenter().subtract(loc).length() <= radius){
                        ((LivingEntity)e).damage(10, player);
                    }
                }
                for(Vector v : subLoc){
                    v.rotateAroundNonUnitAxis(dir, circleSpeed);
                    world.spawnParticle(Particle.FLAME, v.clone().add(loc).toLocation(world), 5, 0, 0, 0, 0);
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }
}
