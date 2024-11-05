package net.gmip.core;

import me.ulrich.koth.api.KothAPIManager;
import net.gmip.core.manager.EventManager;
import net.gmip.core.manager.config.ConfigManager;
import net.gmip.core.manager.cooldown.CooldownManager;
import net.gmip.core.manager.plugin.PluginManager;
import net.gmip.core.manager.plugin.PluginStatus;
import net.gmip.core.task.NPCTask;
import net.gmip.core.utils.SchedulerWrapper;
import net.gmip.core.utils.plugin.PluginUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class GMIPCore extends JavaPlugin implements CommandExecutor {

    public final CooldownManager cooldownManager = new CooldownManager(this);
    public final PluginManager pluginManager = new PluginManager(this);
    public final SchedulerWrapper pluginScheduler = new SchedulerWrapper(this);
    public final ConfigManager configManager = new ConfigManager(this, null);
    public final ConfigManager npcConfigManager = new ConfigManager(this, "npc.yml");

    public final List<ConfigManager> configManagers = List.of(configManager, npcConfigManager);
    public KothAPIManager kothAPIManager = null;


    @Override
    public void onEnable() {
        // Plugin startup logic
        new EventManager(this);

        this.getCommand("gmip").setExecutor(this);

        if (getServer().getPluginManager().getPlugin("UltimateKoth") != null) {
            kothAPIManager = new KothAPIManager();
        } else {
            PluginUtils.sendErrorMessage("UltimateKoth not found!");
        }

        pluginManager.configReload();
        PluginUtils.sendWarnMessage(PluginUtils.PLUGIN_NAME + " has been enabled!");
        pluginManager.setStatus(PluginStatus.ENABLED);

        if (npcConfigManager.getBoolean("active")) {
            new NPCTask(this).runTaskTimerAsynchronously(this, 0L, 20L * 60 * npcConfigManager.getInt("npc", "update"));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("GMIPCore v1.0.0");
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




