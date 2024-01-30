package lqw.plugintest.Props;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Landmine implements Listener {
    @EventHandler
    public void onPlayerWalkOnLandmine(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    loc.setX(player.getLocation().getX() + x);
                    loc.setY(player.getLocation().getY() + y);
                    loc.setZ(player.getLocation().getZ() + z);
                    if (loc.getBlock().getType() == Material.STONE_BUTTON) {
                        player.getWorld().createExplosion(loc, 2);
                        break;
                    }
                    if (loc.getBlock().getType() == Material.OAK_BUTTON) {
                        loc.getBlock().setType(Material.AIR);
                        player.getWorld().spawnEntity(loc, EntityType.DRAGON_FIREBALL);
                        break;
                    }
                }
            }
        }
    }
}
