package lqw.plugintest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
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

    public static void indicatorCycle(Player player, Location centerLoc, double dis){
        Vector delta = new Vector(0, 0.1, 0);
        Location loc = centerLoc.clone();
        for(int i = 0;i < 50; i++, loc.add(delta)){
            player.spawnParticle(Particle.CRIT, loc, 1, 0, 0, 0, 0.1);
        }
        delta.setY(0);delta.setX(dis);
        loc = centerLoc.clone();
        for(int i = 0; i <= 360; i ++){
            delta.rotateAroundY(Math.toRadians(i));
            player.spawnParticle(Particle.CRIT, loc.clone().add(delta), 1, 0, 0, 0, 0.2);
        }
    }

    public static boolean isNotUsing(PlayerInteractEvent event, String material){
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() !=Action.RIGHT_CLICK_AIR)return true;
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.valueOf(material))return true;
        if(event.getHand() != EquipmentSlot.HAND)return true;
        return false;
    }
    public static boolean isNotUsing(PlayerInteractEvent event, String material, int index){
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() !=Action.RIGHT_CLICK_AIR)return true;
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.valueOf(material))return true;
        if(event.getHand() != EquipmentSlot.HAND)return true;
        if(event.getPlayer().getInventory().getHeldItemSlot() != index)return true;
        return false;

    }

}
