package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CashBox extends Movable implements Listener {
    public CashBox(){
        blockName = Material.GOLD_BLOCK.name();
    }
    void entitySpawn(Player player) {
        FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(player.getLocation().add(player.getLocation().getDirection()),Material.GOLD_BLOCK.createBlockData());
        fallingBlock.setDropItem(false);
        fallingBlock.setGravity(false);
        fallingBlock.setHurtEntities(false);
        controlEn.put(player.getUniqueId(), fallingBlock);
    }
}
