package lqw.plugintest.Props;
import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhaseRift implements Listener {
    private Vector dir = new Vector(3, 6, 3);
    private int maxDistance = 30, time = 1000;
    HashMap<Location, Location>map = new HashMap<>();
    public void summonStartRift(Location spawnLoc){
        Location loc = spawnLoc.subtract(dir);
        dir.multiply(0.1);
        for(int i = 1; i <= 60; i++){
            spawnLoc.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc,0,0,0,0, 1);
            loc.add(dir);
        }
    }
    public void summonEndRift(Location spawnLoc){
        Location loc = spawnLoc.subtract(dir);
        dir.multiply(0.2);
        for(int i = 1; i <= 20; i++){
            spawnLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc,0, 0, 0, 0, 1);
            loc.add(dir);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
//        if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
//            summonAimingRift(LQW.aimSpace(player.getLocation(), 20));
//        }
        if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
            summonEndRift(LQW.aimSpace(player.getLocation().add(0,1,0), maxDistance));
        }
        Location playerLocation = player.getLocation();
        for (Map.Entry<Location,Location> entry: map.entrySet()) {
            Location startLoc = entry.getKey();
            Location endLoc = entry.getValue();
            if(startLoc.distance(playerLocation) < 1){
                LQW.trans(startLoc, endLoc, player, 5);
            }
        }
    }
    @EventHandler
    public void onUseIronSword(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)return;
        if(event.getHand() != EquipmentSlot.HAND)return;
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if(inventory.getItemInMainHand().getType() != Material.IRON_SWORD)return;
        Location startLoc = player.getLocation(), endLoc = LQW.aimSpace(player.getLocation(), maxDistance);
        if(startLoc.distance(endLoc) < 3)return;
        map.put(startLoc, endLoc);
        LQW.trans(startLoc, endLoc, player, 5);
        new BukkitRunnable(){
            private int timer = 0;

            @Override
            public void run() {
                timer++;
                if(timer > time){
                    map.remove(startLoc);
                    cancel();
                }
                summonStartRift(startLoc);
                summonEndRift(endLoc);
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);

    }

}