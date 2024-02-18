package lqw.plugintest.Props;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class MeteorStrike implements Listener {
    HashMap<UUID, BukkitRunnable>map = new HashMap<>();
    HashMap<UUID, Integer>timeMap = new HashMap<>();
    @EventHandler(priority = EventPriority.LOWEST)
    public void useOrangeDye(PlayerInteractEvent event){
        if(LQW.isNotUsing(event, Material.ORANGE_DYE.name(), 4))return;
        Player player = event.getPlayer();
        if(map.containsKey(player.getUniqueId())){
            if(timeMap.get(player.getUniqueId()).intValue() < 3)return;
            map.get(player.getUniqueId()).cancel();
            map.remove(player.getUniqueId());
            LQW.removeNoInteractPlayer(player, Material.ORANGE_DYE, 4);
            player.setVelocity(new Vector(0, -10, 0));
            player.setInvisible(false);
            player.setFlying(false);
            player.setAllowFlight(false);
            timeMap.remove(player.getUniqueId());
            new BukkitRunnable(){
                int timer = 0;
                @Override
                public void run() {
                    if(player.isOnGround()|| timer > 60){
                        player.getWorld().createExplosion(player, 5, false, false);
                        cancel();
                    }
                    timer ++;
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 50, 0.1, 0.1, 0.1);
                }
            }.runTaskTimer(PluginTest.pluginTest, 0, 1);
        }
        else{
            double height = player.getLocation().getY() + 20;
            Location location = player.getLocation();
            location.setY(height);
            player.teleport(location);
            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                public int timer = 0;
                @Override
                public void run() {
                    LQW.indicatorCycle(player,LQW.aimSpace(player.getLocation().setDirection(new Vector(0, -1, 0)),
                            100) , 10);
                    if(timer > 1200){
                        map.remove(player.getUniqueId());
                        cancel();
                    }
                    timer++;
                    timeMap.put(player.getUniqueId(), Integer.valueOf(timer));
                }
            };
            bukkitRunnable.runTaskTimer(PluginTest.pluginTest,0, 1);
            LQW.addNoInteractPlayer(player, Material.ORANGE_DYE, 4);
            map.put(player.getUniqueId(), bukkitRunnable);
            player.setInvisible(true);
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }
}
