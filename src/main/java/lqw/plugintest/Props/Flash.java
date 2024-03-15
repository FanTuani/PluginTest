package lqw.plugintest.Props;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Flash implements Listener {
    @EventHandler
    public void onUsingFlash(PlayerInteractEvent e){
        if(LQW.isNotUsing(e, Material.POPPED_CHORUS_FRUIT.name()))return;
        Player player = e.getPlayer();
        Vector startLoc = player.getLocation().toVector(), dir = player.getLocation().getDirection(), vDir, v, endLoc, displacement;
        double displacementDistance = 10, speed = 0.5;
        startLoc.setY(0);
        dir.setY(0);
        dir.normalize();
        vDir = v = displacement = dir;
        displacement.multiply(displacementDistance);
        endLoc = new Vector(startLoc.getX(), startLoc.getY(), startLoc.getZ());
        endLoc.add(displacement);
        v.multiply(speed);
        player.setVelocity(v);
        new BukkitRunnable(){
            public int timer = 0;
            @Override
            public void run() {
                Vector nowLoc = player.getLocation().toVector(), nowVDir = player.getVelocity().normalize();
                nowLoc.setY(0);
                nowVDir.setY(0);
                if(startLoc.distance(nowLoc) >= startLoc.distance(endLoc)){
                    player.setVelocity(new Vector(0,0,0));
                    cancel();
                }
                if(nowVDir.angle(vDir) > 0.1 || timer > 30){
                    player.setVelocity(new Vector(0,0,0));
                    cancel();
                }
                timer ++;
            }
        }.runTaskTimer(PluginTest.pluginTest,0,1);
    }
}
