package net.gmip.core.manager;

import net.gmip.core.GMIPCore;
import net.gmip.core.event.crafterevent.CrafterEvent;
import net.gmip.core.event.explosion.ExplosionEvent;
import net.gmip.core.event.canceldragon.OnPlayerInteract;
import net.gmip.core.event.claimfly.ClaimFlyEvent;
import net.gmip.core.event.kothbossbar.OnKothStartEvent;
import net.gmip.core.event.staffCommandEvents.staffEvents;
import net.gmip.core.event.voiddamage.OnEntityDamageEvent;
import net.gmip.core.utils.Staff;

public class EventManager {

    private GMIPCore plugin = null;
    private Staff staff; // Añadido para almacenar la instancia de Staff

    public EventManager(GMIPCore plugin) {
        this.plugin = plugin;
        this.staff = new Staff(plugin); // Crear la instancia de Staff
        this.registerEvents();
    }

    public void registerEvents() {
        // Registrar los eventos
        plugin.getServer().getPluginManager().registerEvents(new OnKothStartEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new OnEntityDamageEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ClaimFlyEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ExplosionEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new CrafterEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new OnPlayerInteract(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new staffEvents(plugin, staff), plugin);
    }
}
