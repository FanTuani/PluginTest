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
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Movable implements Listener {
    protected static HashMap<UUID, Entity> controlEn = new HashMap<>();
    public static PluginTest plugin = PluginTest.pluginTest;
    protected String blockName, entityName;

    public Entity entity;
    abstract void effect(Player player);
    @EventHandler
    public void pickUp(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(block == null)return;
        if (block.getType().name().equals(blockName)) {
            block.setType(Material.AIR);
            if (!controlEn.containsKey(player.getUniqueId())) {
                entity = player.getWorld().spawnEntity(player.getLocation() ,EntityType.valueOf(entityName));

                    player.sendMessage("1");
                    entity.setVelocity(new Vector(0,1,0));
                controlEn.put(player.getUniqueId(), entity);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                            player.sendMessage("1");
                        if (!controlEn.containsKey(player.getUniqueId())) {
                            controlEn.remove(player.getUniqueId());
                            cancel();
                        } else {
                            Vector vector = player.getLocation().getDirection();
                            Location teleLocation = player.getLocation().add(vector.multiply(3));
                            entity.teleport(teleLocation);
                            entity.setFallDistance(0);
                        }
                    }
                }.runTaskTimer(PluginTest.pluginTest, 0, 1);
            }
        }
    }

    @EventHandler
    public void giveUP(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        if(controlEn.containsKey(player.getUniqueId())){
            controlEn.remove(player.getUniqueId());
            effect(player);
        }
    }
}
