package net.gmip.core.manager;

import net.gmip.core.GMIPCore;
import net.gmip.core.event.crafterevent.CrafterEvent;
import net.gmip.core.event.explosion.ExplosionEvent;
import net.gmip.core.event.canceldragon.OnPlayerInteract;
import net.gmip.core.event.claimfly.ClaimFlyEvent;
import net.gmip.core.event.kothbossbar.OnKothStartEvent;
import net.gmip.core.event.staffCommandEvents.onPlayerInteractEvent;
import net.gmip.core.event.voiddamage.OnEntityDamageEvent;

public class EventManager {

    private GMIPCore plugin = null;

    public EventManager(GMIPCore plugin) {
        this.plugin = plugin;
        this.registerEvents();
    }

    public void registerEvents() {

        plugin.getServer().getPluginManager().registerEvents(new OnKothStartEvent(), plugin);


        // Void Damage
        plugin.getServer().getPluginManager().registerEvents(new OnEntityDamageEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ClaimFlyEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ExplosionEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new CrafterEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new OnPlayerInteract(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new onPlayerInteractEvent(), plugin);
    }

}
