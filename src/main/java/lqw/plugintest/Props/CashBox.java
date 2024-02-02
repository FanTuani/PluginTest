package lqw.plugintest.Props;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;

public class CashBox extends Movable implements Listener {
    public CashBox() {
        blockName = Material.GOLD_BLOCK.name();
    }

    void entitySpawn(Player player) {
//        FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(player.getLocation().add(player.getLocation().getDirection()), Material.GOLD_BLOCK.createBlockData());
//        fallingBlock.setDropItem(false);
//        fallingBlock.setGravity(false);
//        fallingBlock.setHurtEntities(false);
//        controlEn.put(player.getUniqueId(), fallingBlock);
        Entity entity =
                player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(EntityType.SHULKER.name()));
        Shulker shulker = (Shulker) entity;
        shulker.setAI(false);
        controlEn.put(player.getUniqueId(), entity);
    }

    @Override
    void effect(Player player) {
        super.effect(player);
    }
}
