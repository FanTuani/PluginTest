package lqw.plugintest;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class LQW {
    public static Set<UUID> isTransforming = new HashSet<>();
    public static Location aimSpace(Location startLoc, double maxDistance){
        Vector dir = startLoc.getDirection(), loc = startLoc.toVector(), start = startLoc.toVector();
        World world = startLoc.getWorld();
        while(true){
            loc.add(dir);
            if(!world.getBlockAt(loc.toLocation(world)).isPassable()){
                loc.subtract(dir);
                return loc.toLocation(world);
            }
            if(start.distance(loc)>=maxDistance){
                return loc.toLocation(world);
            }
        }
    }
    public static void trans(Location startLoc, Location endLoc, Player player, int speed){
        if(isTransforming.contains(player.getUniqueId()))return;
        isTransforming.add(player.getUniqueId());
        Vector dir = endLoc.toVector().subtract(startLoc.toVector()).normalize(), v;
        v = dir;
        v.multiply(speed);
        GameMode gameMode = player.getGameMode();
        player.setGameMode(GameMode.SPECTATOR);

        Entity creeper = player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
        player.setSpectatorTarget(creeper);

        creeper.setVelocity(dir);
//        creeper.teleport(creeper.getLocation().setDirection(dir));

        new BukkitRunnable(){
            @Override
            public void run() {
                player.getLocation().setDirection(dir);
                player.setVelocity(v);
                if(startLoc.distance(player.getLocation()) > startLoc.distance(endLoc)){
                    player.setVelocity(new Vector(0,0,0));
                    player.teleport(endLoc.setDirection(player.getLocation().getDirection()));
                    player.setGameMode(gameMode);
                    isTransforming.remove(player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }
}
