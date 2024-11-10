package net.gmip.core.event.bedrespawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class CancelBed implements Listener {

    @EventHandler
    public void cancel(@NotNull PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void cancel(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().name().contains("BED")) {
            event.setCancelled(true);
        }
    }
}
