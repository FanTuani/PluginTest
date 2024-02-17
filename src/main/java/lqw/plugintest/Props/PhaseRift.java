package lqw.plugintest.Props;

import lqw.plugintest.LQW;
import lqw.plugintest.PluginTest;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class PhaseRift implements Listener {
    public static Set<UUID> isTransforming = new HashSet<>();
    private final Vector dir = new Vector(3, 6, 3);
    private final int maxDistance = 50, time = 1000;
    HashMap<Location, Location> map = new HashMap<>();

    private void trans(Location startLoc, Location endLoc, Player player, double speed) {
        if (isTransforming.contains(player.getUniqueId())) return;
        isTransforming.add(player.getUniqueId());
        Vector dir = endLoc.toVector().subtract(startLoc.toVector()).normalize(), v;
        v = dir;
        v.multiply(speed);
        GameMode gameMode = player.getGameMode();
        player.setGameMode(GameMode.SPECTATOR);

        Enderman enderman = (Enderman) player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDERMAN);
        enderman.setSilent(true);
        enderman.setRemoveWhenFarAway(false);
        player.setSpectatorTarget(enderman);
        enderman.setVelocity(v);
        enderman.setInvisible(true);
        enderman.setInvulnerable(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                enderman.teleport(enderman.getLocation().setDirection(dir));
                enderman.setVelocity(v);
                player.setSpectatorTarget(enderman);
                if (startLoc.distance(player.getLocation()) > startLoc.distance(endLoc)) {
                    enderman.setVelocity(new Vector(0, 0, 0));
                    enderman.teleport(endLoc.setDirection(enderman.getLocation().getDirection()));
                    player.setGameMode(gameMode);
                    enderman.remove();
                    isTransforming.remove(player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);
    }

    public void summonStartRift(Location spawnLoc) {
        Location loc = spawnLoc.subtract(dir);
        dir.multiply(0.1);
        for (int i = 1; i <= 60; i++) {
            spawnLoc.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc, 0, 0, 0, 0, 1);
            loc.add(dir);
        }
    }

    public void summonEndRift(Location spawnLoc) {
        Location loc = spawnLoc.subtract(dir);
        dir.multiply(0.2);
        for (int i = 1; i <= 20; i++) {
            spawnLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 0, 0, 0, 0, 1);
            loc.add(dir);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
//        if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
//            summonAimingRift(LQW.aimSpace(player.getLocation(), 20));
//        }
        if (player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
            Location loc = LQW.aimSpace(player.getEyeLocation(), maxDistance);
            loc.setDirection(new Vector(loc.getDirection().getX(), -1, loc.getDirection().getX()));
            loc = LQW.aimSpace(loc, 100);
            LQW.indicatorCycle(player, loc, 1);
        }
        Location playerLocation = player.getLocation();
        for (Map.Entry<Location, Location> entry : map.entrySet()) {
            Location startLoc = entry.getKey();
            Location endLoc = entry.getValue();
            if (startLoc.distance(playerLocation) < 1) {
                trans(startLoc, endLoc, player, 5);
            }
        }
    }

    @EventHandler
    public void onUseIronSword(PlayerInteractEvent event) {
        if (LQW.isNotUsing(event, Material.IRON_SWORD.name())) return;
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if (inventory.getItemInMainHand().getType() != Material.IRON_SWORD) return;
        Location startLoc = player.getLocation(), endLoc = LQW.aimSpace(player.getEyeLocation(), maxDistance);
        if (startLoc.distance(endLoc) < 3) return;
        map.put(startLoc, endLoc);
        trans(startLoc, endLoc, player, 5);
        new BukkitRunnable() {
            private int timer = 0;

            @Override
            public void run() {
                timer++;
                if (timer > time) {
                    map.remove(startLoc);
                    cancel();
                }
                summonStartRift(startLoc);
                summonEndRift(endLoc);
            }
        }.runTaskTimer(PluginTest.pluginTest, 0, 1);

    }

}
