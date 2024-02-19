package lqw.plugintest.Props.BatKing;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BatSpawner implements Listener {
    @EventHandler
    public void p(PlayerInteractEvent event){
        if(LQW.isNotUsing(event, Material.PURPLE_DYE.name()))return;
        Bat bat = new Bat();
        bat.SpawnBat(event.getPlayer(), event.getPlayer().getLocation());
    }
}
