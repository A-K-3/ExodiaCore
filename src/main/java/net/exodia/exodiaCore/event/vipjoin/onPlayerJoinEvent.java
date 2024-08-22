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
            JoinEffects soundEffect = new JoinEffects(plugin);
            // Primero de todo ejecutamos esta función para guardar TODAS las variables de la config.
            soundEffect.printPermissionNodes(player);

            // Ejecutamos un sonido cada vez que el usuario entra
            soundEffect.playsound(player);

        }, 20L);
    }
}
