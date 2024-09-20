package net.exodia.exodiaCore.manager;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.event.kothbossbar.onKothStartEvent;

public class EventManager {

    private ExodiaCore plugin = null;

    public EventManager(ExodiaCore plugin) {
        this.plugin = plugin;
        this.registerEvents();
    }

    public void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new onKothStartEvent(), plugin);
    }
}
