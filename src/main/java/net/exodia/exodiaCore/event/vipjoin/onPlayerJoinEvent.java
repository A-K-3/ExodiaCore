package net.exodia.exodiaCore.event.vipjoin;

import net.exodia.exodiaCore.event.ExodiaEvent;
import net.exodia.exodiaCore.vipjoin.JoinEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoinEvent extends ExodiaEvent {

    public onPlayerJoinEvent() {
        super();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();

        plugin.pluginScheduler.doAsyncLater(() -> {
            JoinEffects effects = new JoinEffects(plugin);
            effects.getPlayerPermission(player);
            effects.playsound(player);
            effects.particleeffect(player);
            // Hacer el Rayo en el hilo principal (cosas de spigot)
            Bukkit.getScheduler().runTask(plugin, () -> {
                effects.lightning(player);
            });

        }, 20L);
    }
}
