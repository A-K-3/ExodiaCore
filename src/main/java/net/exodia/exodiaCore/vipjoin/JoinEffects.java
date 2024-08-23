package net.exodia.exodiaCore.vipjoin;

import net.exodia.exodiaCore.ExodiaCore;
import net.exodia.exodiaCore.utils.plugin.PluginUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
    private String node;

    private final ExodiaCore plugin;


    public JoinEffects(ExodiaCore plugin) {
        this.plugin = plugin;
    }

    public void playsound(Player player) {
        if (sound == null) {
            plugin.getLogger().warning("El nombre del sonido no está configurado para el grupo: " + node);
            return;
        }

        try {
            Sound finalSound = Sound.valueOf(sound.toUpperCase());
            player.playSound(player.getLocation(), finalSound, (float) volume, (float) pitch);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("El sonido configurado '" + sound + "' para el grupo '" + node + "' no es válido.");
        }

    }

    public void particleeffect(Player player) {
        Particle particlefinal;

        try {
            particlefinal = Particle.valueOf(particle);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Partícula inválida en la config: " + particle);
            return;
        }

        player.getWorld().spawnParticle(particlefinal, player.getLocation(), particleAmount, offsetX, offsetY, offsetZ, optionalValue);

    }

    public void lightning(Player player) {
        if (lightning) {
            player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
        }
    }

    public void getPlayerPermission(Player player) {
        ConfigurationSection seccion = plugin.getConfig().getConfigurationSection("vip-join.permissions");

        ConfigurationSection seccionDefault = plugin.getConfig().getConfigurationSection("vip-join.");
        String defaultnode = "";

        if (seccion != null) {
            Set<String> nodos = seccion.getKeys(false);
            for (String nodo : nodos) {
                if (player.hasPermission(nodo)) {
                    setConfigVariables(seccion, nodo);
                    return;
                }
            }
            setConfigVariables(seccionDefault, defaultnode); // Default
        }
    }

    private void setConfigVariables(ConfigurationSection permissionsSection, String node) {
        String defaultSound = plugin.configManager.get("vip-join.default-join", "sound");
        double defaultVolume = plugin.configManager.getDouble("vip-join.default-join", "volume");
        double defaultPitch = plugin.configManager.getDouble("vip-join.default-join", "pitch");
        boolean defaultLightning = plugin.configManager.getBoolean("vip-join.default-join", "lightning");
        String defaultParticles = plugin.configManager.get("vip-join.default-join", "particle");
        int defaultParticleAmount = plugin.configManager.getInt("vip-join.default-join", "particle-amount");
        double defaultOffsetX = plugin.configManager.getDouble("vip-join.default-join", "offset-x");
        double defaultOffsetY = plugin.configManager.getDouble("vip-join.default-join", "offset-y");
        double defaultOffsetZ = plugin.configManager.getDouble("vip-join.default-join", "offset-z");
        double defaultOptionalValue = plugin.configManager.getDouble("vip-join.default-join", "optional-value");

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