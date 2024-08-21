package net.exodia.exodiaCore.utils.plugin;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.manager.plugin.PluginStatus;
import net.exodia.exodiaCore.utils.plugin.message.ExodusMessageType;
import net.exodia.exodiaCore.utils.plugin.message.MessageUtils;
import org.bukkit.Bukkit;

public class PluginUtils {

    public static final ExodiaCore PLUGIN = ExodiaCore.getPlugin(ExodiaCore.class);
    public static final String PLUGIN_NAME = PLUGIN.getDescription().getName();
    public static final String PREFIX = PLUGIN.getConfigManager().get("messages", "prefix");
    private static final String SEPARATOR = "&e&m========================================================================";

    public static void sendWarnMessage(String message) {
        sendConsoleMessage(SEPARATOR);
        sendConsoleMessage(ExodusMessageType.WARN.get() + message);
        sendConsoleMessage(SEPARATOR);
    }

    public static void sendErrorMessage(String message) {
        sendConsoleMessage(SEPARATOR);
        sendConsoleMessage("");
        sendConsoleMessage("");
        sendConsoleMessage(ExodusMessageType.ERROR.get() + message);
        sendConsoleMessage("");
        sendConsoleMessage("");
        sendConsoleMessage(SEPARATOR);
    }

    public static void sendDebugMessage(String message) {
        if (!PLUGIN.getConfigManager().getBoolean("general", "debug"))
            return;
        sendConsoleMessage(ExodusMessageType.DEBUG.get() + message);
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.colorize(message));
    }

    public static void disablePlugin() {
        if (PLUGIN.getPluginStatus().isDisabling() || PLUGIN.getPluginStatus().isDisabled())
            return;

        PLUGIN.getPluginStatus().setStatus(PluginStatus.DISABLING);
        PLUGIN.getServer().getPluginManager().disablePlugin(PLUGIN);
    }

}
