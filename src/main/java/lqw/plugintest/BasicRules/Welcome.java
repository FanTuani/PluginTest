package lqw.plugintest.BasicRules;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;
import lqw.plugintest.LQW;

public class Welcome implements Listener {
    @EventHandler
    public void welcomePlayer(PlayerJoinEvent event) {
        Player thisPlayer = event.getPlayer();
        LQW.lastVecLoc.put(thisPlayer, thisPlayer.getLocation().toVector());
        LQW.nowVelocity.put(thisPlayer, new Vector(0, 0, 0));
        for (Player player : event.getPlayer().getServer().getOnlinePlayers()) {
            int onlineCount = event.getPlayer().getServer().getOnlinePlayers().size();
            String subTitle = ChatColor.YELLOW + "Online Player Count: " + onlineCount;
            if (player.getName().equals(event.getPlayer().getName())) {
                player.sendTitle(ChatColor.GREEN + "Welcome!", subTitle, 10, 30, 10);
            } else {
                player.sendTitle(ChatColor.GREEN + event.getPlayer().getName() + " Joined!", subTitle, 5, 10, 5);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }
}
