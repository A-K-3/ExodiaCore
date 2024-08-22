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
        String defaultSound = plugin.configManager.get("default-join", "sound");
        double defaultVolume = plugin.configManager.getDouble("default-join", "volume");
        double defaultPitch = plugin.configManager.getDouble("default-join", "pitch");
        boolean defaultLightning = plugin.configManager.getBoolean("default-join", "lightning");
        String defaultParticles = plugin.configManager.get("default-join", "particle");
        int defaultParticleAmount = plugin.configManager.getInt("default-join", "particle-amount");
        double defaultOffsetX = plugin.configManager.getDouble("default-join", "offset-x");
        double defaultOffsetY = plugin.configManager.getDouble("default-join", "offset-y");
        double defaultOffsetZ = plugin.configManager.getDouble("default-join", "offset-z");
        double defaultOptionalValue = plugin.configManager.getDouble("default-join", "optional-value");

        //TODO: Arreglar esta parte en la config para que funcione
        String sound = plugin.configManager.get(permissionsSection.getName() + "." + node, "sound", defaultSound);
        double volume = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "volume", defaultVolume);
        double pitch = plugin.configManager.getDouble(permissionsSection.getName() + "." + node, "pitch", defaultPitch);
        boolean lightning = plugin.configManager.getBoolean(permissionsSection.getName() + "." + node, "lightning", defaultLightning);
        String particle = plugin.configManager.get(permissionsSection.getName() + "." + node, "particle", defaultParticles);
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