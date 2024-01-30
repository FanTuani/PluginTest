package lqw.plugintest.Props;

import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class FireExtinguisher extends Movable implements Listener {
    public FireExtinguisher(){
        blockName = new String(Material.TNT.name());
        entityName = new String(EntityType.VILLAGER.name());
    }
    @Override
    void effect(Player player) {
        entity.remove();
    }
}
