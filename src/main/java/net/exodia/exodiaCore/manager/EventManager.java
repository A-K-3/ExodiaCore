package net.exodia.exodiaCore.manager;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.event.claimfly.ClaimFlyEvent;
import net.exodia.exodiaCore.event.kothbossbar.OnKothStartEvent;
import net.exodia.exodiaCore.event.voiddamage.OnEntityDamageEvent;

public class EventManager {

    private ExodiaCore plugin = null;

    public EventManager(ExodiaCore plugin) {
        this.plugin = plugin;
        this.registerEvents();
    }

    public void registerEvents() {

        plugin.getServer().getPluginManager().registerEvents(new OnKothStartEvent(), plugin);


        // Void Damage
        plugin.getServer().getPluginManager().registerEvents(new OnEntityDamageEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ClaimFlyEvent(), plugin);
    }

}
