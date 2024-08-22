package net.exodia.exodiaCore.manager.plugin;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.manager.config.ConfigManager;

public class PluginManager {

    private PluginStatus status = PluginStatus.STARTING;
    private ExodiaCore plugin = null;

    public PluginManager(ExodiaCore plugin) {
        this.plugin = plugin;
    }

    public void disablePlugin() {
        if (isDisabling() || isDisabled()) return;

        setStatus(PluginStatus.DISABLING);
        plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    public void configReload() {
        plugin.configManagers.forEach(ConfigManager::reload);
    }

    public boolean isStatus(PluginStatus status) {
        return this.status == status;
    }


    public boolean isEnabled() {
        return isStatus(PluginStatus.ENABLED);
    }

    public boolean isDisabled() {
        return isStatus(PluginStatus.DISABLED);
    }

    public boolean isDisabling() {
        return isStatus(PluginStatus.DISABLING);
    }

    public PluginStatus getStatus() {
        return status;
    }

    public void setStatus(PluginStatus status) {
        this.status = status;
    }
}