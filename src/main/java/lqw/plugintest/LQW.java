package lqw.plugintest;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class LQW {
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
}
