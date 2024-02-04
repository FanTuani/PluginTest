package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Flash implements Listener {

    @EventHandler
    public void onUsingFlash(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR)return;
        if(e.getHand() != EquipmentSlot.HAND)return;
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if(inventory.getItemInMainHand().getType() != Material.POPPED_CHORUS_FRUIT)return;
        Vector loc = player.getLocation().toVector(), dir = player.getLocation().getDirection(), vDir;
        vDir = new Vector(dir.getX(), 0, dir.getZ()).normalize();
        player.setVelocity(vDir.multiply(10));
        new BukkitRunnable(){
            @Override
            public void run() {
                player.setVelocity(new Vector(0,0,0));
                cancel();
            }
        }.runTaskTimer(PluginTest.pluginTest, 3, 1);
    }
}
