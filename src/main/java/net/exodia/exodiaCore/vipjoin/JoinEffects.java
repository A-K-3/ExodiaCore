package net.exodia.exodiaCore.vipjoin;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.utils.plugin.PluginUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

public class JoinEffects {

    private final ExodiaCore plugin;

    public JoinEffects(ExodiaCore plugin) {
        this.plugin = plugin;
    }

    public void playsound(Player player) {
        String group = "test";

        printPermissionNodes();

        PluginUtils.sendConsoleMessage("Permiso: " + group);

    }

    private void printPermissionNodes() {
        ConfigurationSection vipJoinSection = plugin.configManager.getSection("vip-join");
        ConfigurationSection permissionsSection = plugin.configManager.getSection(vipJoinSection, "permissions");
        if (permissionsSection != null) {
            Set<String> permissionNodes = plugin.configManager.getKeys(permissionsSection);
            for (String node : permissionNodes) {
                PluginUtils.sendConsoleMessage("Nodo: " + node);
                printNodeDetails(permissionsSection, node);
            }
        }
    }

    private void printNodeDetails(ConfigurationSection permissionsSection, String node) {
        String defaultSound = getDefault("default-join.sound");
        int defaultVolume = getDefaultInt("default-join.volume");
        int defaultPitch = getDefaultInt("default-join.pitch");
        boolean defaultLightning = getDefaultBoolean("default-join.lightning");
        boolean defaultParticles = getDefaultBoolean("default-join.particle");
        int defaultParticleAmount = getDefaultInt("default-join.particle-amount");
        int defaultOffsetX = getDefaultInt("default-join.offset-x");
        int defaultOffsetY = getDefaultInt("default-join.offset-y");
        int defaultOffsetZ = getDefaultInt("default-join.offset-z");
        int defaultOptionalValue = getDefaultInt("default-join.optional-value");

        String sound = plugin.configManager.get(permissionsSection.getName() + "." + node, "sound", defaultSound);
        double volume = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "volume", defaultVolume);
        double pitch = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "pitch", defaultPitch);
        boolean lightning = plugin.configManager.getBoolean(permissionsSection.getName() + "." + node, "lightning", defaultLightning);
        boolean particles = plugin.configManager.getBoolean(permissionsSection.getName() + "." + node, "particle", defaultParticles);
        int particleAmount = plugin.configManager.getInt(permissionsSection.getName() + "." + node, "particle-amount", defaultParticleAmount);
        double offsetX = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "offset-x", defaultOffsetX);
        double offsetY = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "offset-y", defaultOffsetY);
        double offsetZ = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "offset-z", defaultOffsetZ);
        double optionalValue = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "optional-value", defaultOptionalValue);
    }

    private String getDefault(String path) {
        return plugin.configManager.get("vip-join", path);
    }

    private int getDefaultInt(String path) {
        return plugin.configManager.getInt("vip-join", path);
    }

    private boolean getDefaultBoolean(String path) {
        return plugin.configManager.getBoolean("vip-join", path);
    }
}