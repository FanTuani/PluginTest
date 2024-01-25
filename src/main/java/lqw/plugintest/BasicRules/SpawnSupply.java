package lqw.plugintest.BasicRules;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpawnSupply implements Listener {
    private final String[] supplies = new String[]{"COOKIE", "CLAY", "CLAY"};
    private final int[] suppliesNum = new int[]{0, 0, 0};

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
