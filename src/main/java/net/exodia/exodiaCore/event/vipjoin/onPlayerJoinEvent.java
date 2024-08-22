package net.exodia.exodiaCore.event.vipjoin;

import net.exodia.exodiaCore.event.ExodiaEvent;
import net.exodia.exodiaCore.vipjoin.JoinEffects;
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
            // Ejecutamos un sonido cada vez que el usuario entra
            JoinEffects soundEffect = new JoinEffects(plugin);
            soundEffect.playsound(player);

        }, 20L);
    }
}
