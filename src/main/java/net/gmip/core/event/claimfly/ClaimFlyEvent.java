package net.gmip.core.event.claimfly;

import net.gmip.core.event.ExodiaEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ClaimFlyEvent extends ExodiaEvent{

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage();

        if (message.equalsIgnoreCase("/ps fly")) {
            Player player = event.getPlayer();
            Bukkit.dispatchCommand(player, "claimfly");
            event.setCancelled(true);
        }
    }
}
