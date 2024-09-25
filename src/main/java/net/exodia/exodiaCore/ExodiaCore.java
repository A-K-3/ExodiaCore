package net.exodia.exodiaCore;

import me.ulrich.koth.api.KothAPIManager;
import net.exodia.exodiaCore.manager.EventManager;
import net.exodia.exodiaCore.manager.config.ConfigManager;
import net.exodia.exodiaCore.manager.cooldown.CooldownManager;
import net.exodia.exodiaCore.manager.plugin.PluginManager;
import net.exodia.exodiaCore.manager.plugin.PluginStatus;
import net.exodia.exodiaCore.utils.SchedulerWrapper;
import net.exodia.exodiaCore.utils.plugin.PluginUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ExodiaCore extends JavaPlugin implements CommandExecutor {

    public final CooldownManager cooldownManager = new CooldownManager(this);
    public final PluginManager pluginManager = new PluginManager(this);
    public final SchedulerWrapper pluginScheduler = new SchedulerWrapper(this);
    public final ConfigManager configManager = new ConfigManager(this, null);

    public final List<ConfigManager> configManagers = List.of(configManager);
    public KothAPIManager kothAPIManager = null;


    @Override
    public void onEnable() {
        // Plugin startup logic
        new EventManager(this);

        this.getCommand("exodia").setExecutor(this);


        pluginManager.configReload();
        PluginUtils.sendWarnMessage(PluginUtils.PLUGIN_NAME + " has been enabled!");
        pluginManager.setStatus(PluginStatus.ENABLED);

        kothAPIManager = new KothAPIManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("ExodiaCore v1.0.0");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            pluginManager.configReload();
            sender.sendMessage("Config reloaded!");
            return true;
        }
        return false;
    }
}




