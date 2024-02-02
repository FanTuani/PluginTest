package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
    protected String blockName, entityName;

    void effect(Player player) {
        controlEn.get(player.getUniqueId()).setVelocity(player.getLocation().getDirection());
        controlEn.get(player.getUniqueId()).setGravity(true);
    }

    void entitySpawn(Player player) {
        Entity entity =
                player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(entityName));
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
                    Location loc = block.getLocation();
                    loc.setY(loc.getY() + 1);
                    loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, 1, 1);
                    loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 100, 0, 0, 0, 1, block.getBlockData());
                    block.setType(Material.AIR);
                    entitySpawn(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!controlEn.containsKey(player.getUniqueId())) {
                                controlEn.remove(player.getUniqueId());
                                player.playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1, 1);
                                cancel();
                            } else {
                                Vector direction = player.getLocation().getDirection();
                                Location teleLocation = player.getLocation().add(direction.multiply(3));
                                Entity entity = controlEn.get(player.getUniqueId());
                                if (entity.getLocation().distance(player.getLocation()) > 3) {
                                    entity.teleport(teleLocation);
                                } else {
                                    if (teleLocation.getBlock().getType() != Material.AIR) {
                                        teleLocation = player.getLocation();
                                    }
                                    Vector vector = teleLocation.toVector().subtract(entity.getLocation().toVector());
                                    entity.setVelocity(vector);
                                }
                            }
                        }
                    }.runTaskTimer(PluginTest.pluginTest, 0, 1);
                    event.setCancelled(true);
                }
            }
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (controlEn.containsKey(player.getUniqueId())) {
                effect(player);
                controlEn.remove(player.getUniqueId());
            }
        }
    }
}
