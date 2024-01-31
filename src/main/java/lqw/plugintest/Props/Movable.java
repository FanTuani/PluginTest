package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public abstract class Movable implements Listener {
    protected static HashMap<UUID, Entity> controlEn = new HashMap<>();
    public static PluginTest plugin = PluginTest.pluginTest;
    protected String blockName, entityName;

    void effect(Player player) {
        controlEn.get(player.getUniqueId()).setVelocity(player.getLocation().getDirection());
        controlEn.get(player.getUniqueId()).setGravity(true);
    }

    void entitySpawn(Player player) {
        Entity entity =
                player.getWorld().spawnEntity(player.getLocation().add(player.getLocation().getDirection().multiply(3)), EntityType.valueOf(entityName));
        controlEn.put(player.getUniqueId(), entity);
    }

    @EventHandler
    public void pickUp(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block == null) return;
            if (!controlEn.containsKey(player.getUniqueId())) {
                if (block.getType().name().equals(blockName)) {
                    block.setType(Material.AIR);
                    entitySpawn(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
//                            player.sendMessage("1");
                            if (!controlEn.containsKey(player.getUniqueId())) {
                                controlEn.remove(player.getUniqueId());
                                cancel();
                            } else {
                                Vector vector = player.getLocation().getDirection();
                                Location teleLocation = player.getLocation().add(vector.multiply(3));
                                controlEn.get(player.getUniqueId()).teleport(teleLocation);
                                controlEn.get(player.getUniqueId()).setFallDistance(0);
                            }
                        }
                    }.runTaskTimer(PluginTest.pluginTest, 0, 1);
                }
            }
        } else {
            Player player = event.getPlayer();
            if (controlEn.containsKey(player.getUniqueId())) {
                effect(player);
                controlEn.remove(player.getUniqueId());
            }
        }
    }
}
