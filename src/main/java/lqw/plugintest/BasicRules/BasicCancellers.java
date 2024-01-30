package lqw.plugintest.BasicRules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BasicCancellers implements Listener {
    @EventHandler
    public void noHunger(FoodLevelChangeEvent event) {
        event.getEntity().setFoodLevel(20);
        event.setCancelled(true);
    }

    @EventHandler
    public void noFallDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noRegainHealth(EntityRegainHealthEvent event) {
        event.setCancelled(true);
    }
}
