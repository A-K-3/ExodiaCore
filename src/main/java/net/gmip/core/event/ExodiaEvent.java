package net.gmip.core.event;

import net.gmip.core.GMIPCore;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class ExodiaEvent implements Listener {

    /**
     * Clase para los eventos, haciendo un extends de esta clase ya tendremos
     * todos los métodos y constructores necesarios para poder usar comandos y acceder
     * a la instancia del Plugin fácilmente
     */
    protected GMIPCore plugin = GMIPCore.getPlugin(GMIPCore.class);
    private boolean notifyInCooldown = false;

    protected boolean hasPermission(@NotNull Player player, String permission) {
        return player.hasPermission(permission) || player.isOp();
    }
}
