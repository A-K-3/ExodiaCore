package net.gmip.core.event.bedrespawn;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class CancelBed implements Listener {

    @EventHandler
    public void cancel(PlayerSetSpawnEvent event) {
        if (event.getCause().equals(PlayerSetSpawnEvent.Cause.BED)) {
            event.setCancelled(true);
        }
    }
}
