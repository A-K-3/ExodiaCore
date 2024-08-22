package net.exodia.exodiaCore.vipjoin;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.utils.plugin.PluginUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JoinEffects {

    private String sound;
    private double volume;
    private double pitch;
    private boolean lightning;
    private String particle;
    private int particleAmount;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double optionalValue;

    private final ExodiaCore plugin;

    public JoinEffects(ExodiaCore plugin) {
        this.plugin = plugin;
    }

    public void playsound(Player player) {
        // TODO: Hacer el playsound y los demás efectos del antiguo plugin ahora teniendo todos los valores de la config guardados en variables
        String group = "test";

        PluginUtils.sendConsoleMessage("Permiso: " + group);

        System.out.println(sound);

    }

    public void printPermissionNodes(Player player) {
        ConfigurationSection seccion = plugin.getConfig().getConfigurationSection("vip-join.permissions");
        if (seccion != null) {
            Set<String> nodos = seccion.getKeys(false);
            for (String nodo : nodos) {
                if (player.hasPermission(nodo)) {
                    printNodeDetails(seccion, nodo);
                    return;
                }
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

        String nodePath = "vip-join.permissions." + node;

        // Usamos el this. este para hacer las variables globales y así usarlas en las otras funciones
        this.sound = plugin.configManager.get(nodePath, "sound", defaultSound);
        this.volume = plugin.configManager.getDouble(nodePath, "volume", defaultVolume);
        this.pitch = plugin.configManager.getDouble(nodePath, "pitch", defaultPitch);
        this.lightning = plugin.configManager.getBoolean(nodePath, "lightning", defaultLightning);
        this.particle = plugin.configManager.get(nodePath, "particle", defaultParticles);
        this.particleAmount = plugin.configManager.getInt(nodePath, "particle-amount", defaultParticleAmount);
        this.offsetX = plugin.configManager.getDouble(nodePath, "offset-x", defaultOffsetX);
        this.offsetY = plugin.configManager.getDouble(nodePath, "offset-y", defaultOffsetY);
        this.offsetZ = plugin.configManager.getDouble(nodePath, "offset-z", defaultOffsetZ);
        this.optionalValue = plugin.configManager.getDouble(nodePath, "optional-value", defaultOptionalValue);
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