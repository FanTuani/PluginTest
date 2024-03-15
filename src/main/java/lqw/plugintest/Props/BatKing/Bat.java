package lqw.plugintest.Props.BatKing;

import com.destroystokyo.paper.entity.Pathfinder;
import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Bat implements Listener {
    public int containTime = 2000, attractTime = 10;
    private LivingEntity spawner;
    private LivingEntity enemy, bat;
    private boolean hasEnemy = false;
    public Bat thisClass = this;
    public void SpawnBat(LivingEntity spawner1, Location location){
        spawner = spawner1;
        bat = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.BAT);
        bat.setCustomName(spawner.getName() + "的" + "Bat");
        bat.setCustomNameVisible(false);
        PluginTest.pluginTest.getServer().getPluginManager().registerEvents(this, PluginTest.pluginTest);
        new BukkitRunnable(){
            private int timer = 0;
            @Override
            public void run() {
                timer++;
                if(hasEnemy){
                    Vector dis =  enemy.getLocation().toVector().subtract(bat.getLocation().toVector());
                    if(dis.length()<0.5){
                        if(timer%attractTime == 0)
                            enemy.damage(2, bat);
                    }
                    else if(dis.length() < 10)
                            bat.setVelocity(dis.clone().normalize());
                }
                else{
                    Vector dis =  spawner.getLocation().toVector().subtract(bat.getLocation().toVector());
                    if(dis.length() > 5){
                        if(dis.length() < 10)
                            bat.setVelocity(dis.clone().normalize());
                        else{
                            bat.teleport(spawner);
                        }
                    }

                }
                if(timer > containTime){
                    bat.remove();
                    PluginTest.pluginTest.getServer().getPluginManager().registerEvents(thisClass, PluginTest.pluginTest);
                    cancel();
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager(), entity = event.getEntity();
        if(damager.getUniqueId() == spawner.getUniqueId() && entity instanceof LivingEntity){
            hasEnemy = true;
            enemy = (LivingEntity) entity;
        }
        else if(entity.getUniqueId() == spawner.getUniqueId() && damager instanceof LivingEntity){
            hasEnemy = true;
            enemy = (LivingEntity) damager;
        }
    }
    @EventHandler
    public void dead(EntityDeathEvent event){
        if(event.getEntity().getUniqueId() == enemy.getUniqueId()){
            hasEnemy = false;
        }
    }
}
