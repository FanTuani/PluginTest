package lqw.plugintest.BasicRules;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class SpawnSupply implements Listener {
    private String[] supplies = new String[]{"COOKIE", "CLAY", "CLAY"};
    private int[] suppliesNum = new int[]{0, 0, 0};

    @EventHandler
    public void respawnSupply(PlayerPostRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        player.sendMessage("DIE");
        for (int i = 0; i < supplies.length; i++) {
            ItemStack itemStack = new ItemStack(Material.valueOf(supplies[i]), suppliesNum[i]);
            inventory.addItem(itemStack);
        }
    }
}
