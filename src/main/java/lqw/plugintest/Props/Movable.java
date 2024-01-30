package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Set;

public class Movable implements Listener {
    private Set<String> set;
    private static HashMap<Player, Entity> controlEn = new HashMap<>();
    private PluginTest plugin;

    public Movable(PluginTest pluginTest) {
        plugin = pluginTest;

//        add(EntityType.VILLAGER.name());

    }

    public void add(String s) {
        set.add(s);
    }


    @EventHandler
    public void onPlayerRightClickEntity(PlayerInteractAtEntityEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        player.sendMessage(entity.getType().name());
//        if (set.contains(event.getRightClicked().getType().name())) {

        if (!controlEn.containsKey(player)) {
            controlEn.put(player, entity);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!controlEn.containsKey(player)) {
                        controlEn.remove(player);
                        cancel();
                    } else {
                        Vector vector = player.getLocation().getDirection();
                        Location teleLocation = player.getLocation().add(vector.multiply(3));
                        entity.teleport(teleLocation);
                        entity.setFallDistance(0);
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        } else {
            controlEn.remove(player);
        }

    }
}
