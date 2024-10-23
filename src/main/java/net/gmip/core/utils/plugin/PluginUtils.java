package net.gmip.core.utils.plugin;

import net.gmip.core.GMIPCore;
import net.gmip.core.manager.plugin.PluginStatus;
import net.gmip.core.utils.plugin.message.MessageType;
import net.gmip.core.utils.plugin.message.MessageUtils;
import org.bukkit.Bukkit;

public class PluginUtils {

    public static final GMIPCore PLUGIN = GMIPCore.getPlugin(GMIPCore.class);
    public static final String PLUGIN_NAME = PLUGIN.getDescription().getName();
    public static final String PREFIX = PLUGIN.configManager.get("messages", "prefix");
    private static final String SEPARATOR = "&e&m========================================================================";

    public static void sendWarnMessage(String message) {
        sendConsoleMessage(SEPARATOR);
        sendConsoleMessage(MessageType.WARN.get() + message);
        sendConsoleMessage(SEPARATOR);
    }

    public static void sendErrorMessage(String message) {
        sendConsoleMessage(SEPARATOR);
        sendConsoleMessage("");
        sendConsoleMessage("");
        sendConsoleMessage(MessageType.ERROR.get() + message);
        sendConsoleMessage("");
        sendConsoleMessage("");
        sendConsoleMessage(SEPARATOR);
    }

    public static void sendDebugMessage(String message) {
        if (!PLUGIN.configManager.getBoolean("general", "debug"))
            return;
        sendConsoleMessage(MessageType.DEBUG.get() + message);
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.colorize(message));
    }

    public static void disablePlugin() {
        if (PLUGIN.pluginManager.isDisabling() || PLUGIN.pluginManager.isDisabled())
            return;

        PLUGIN.pluginManager.setStatus(PluginStatus.DISABLING);
        PLUGIN.getServer().getPluginManager().disablePlugin(PLUGIN);
    }

}
