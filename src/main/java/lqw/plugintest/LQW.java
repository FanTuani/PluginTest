package lqw.plugintest;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class LQW implements Listener {
    public static HashMap<Player, Vector> lastVecLoc = new HashMap<>();
    public static HashMap<Player, Vector> nowVelocity = new HashMap<>();

    @EventHandler
    public void calculatePlayerVelocity(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Vector nowVecLoc = player.getLocation().toVector();
        nowVelocity.put(player, nowVecLoc.subtract(lastVecLoc.get(player)));
        lastVecLoc.put(player, player.getLocation().toVector());
    }

    public static Vector getPlayerVelocity(Player player) {
        return nowVelocity.get(player);
    }

    public static Location aimSpace(Location startLoc, double maxDistance) {
        Vector dir = startLoc.getDirection(), loc = startLoc.toVector(), start = startLoc.toVector();
        World world = startLoc.getWorld();
        while (true) {
            loc.add(dir);
            if (!world.getBlockAt(loc.toLocation(world)).isPassable()) {
                loc.subtract(dir);
                return loc.toLocation(world);
            }
            if (start.distance(loc) >= maxDistance) {
                return loc.toLocation(world);
            }
        }
    }
}
