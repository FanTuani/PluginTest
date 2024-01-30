package lqw.plugintest.BasicRules;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;
/*
    The config is supposed to be like this

    respawnSupplies:
      - - itemName1
        - itemNum1
      - - itemName2
        - itemNum2
      - - itemName3
        - itemNum3

     and so on
 */
public class SpawnSupply implements Listener {
    FileConfiguration configuration;
    List respawnSuppluies;
    public SpawnSupply(PluginTest pluginTest){
        configuration = pluginTest.getConfig();
        respawnSuppluies = configuration.getList("respawnSupplies");
    }

    @EventHandler
    public void respawnSupply(PlayerPostRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        for(Object i : respawnSuppluies){
            List list = (List)i;
            ItemStack itemStack = new ItemStack(Material.valueOf(list.get(0).toString()), (int)list.get(1));
            inventory.addItem(itemStack);
        }
    }
}
