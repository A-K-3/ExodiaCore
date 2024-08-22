package net.exodia.exodiaCore.event;

import net.exodia.exodiaCore.ExodiaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class ExodiaEvent implements Listener {

    /**
     * Clase para los eventos, haciendo un extends de esta clase ya tendremos
     * todos los métodos y constructores necesarios para poder usar comandos y acceder
     * a la instancia del Plugin fácilmente
     */
    protected ExodiaCore plugin = ExodiaCore.getPlugin(ExodiaCore.class);
    private boolean notifyInCooldown = false;

    protected boolean hasPermission(@NotNull Player player, String permission) {
        return player.hasPermission(permission) || player.isOp();
    }
}
