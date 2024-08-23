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
        if (particle == null) {
            return;
        }

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

        if (seccion != null) {
            Set<String> nodos = seccion.getKeys(false);
            for (String nodo : nodos) {
                if (player.hasPermission(nodo)) {
                    setConfigVariables(seccion, nodo);
                    return;
                }
            }
        }
    }

    private void setConfigVariables(ConfigurationSection permissionsSection, String node) {
        String nodePath = "vip-join.permissions." + node;

        // Usamos el this. este para hacer las variables globales y así usarlas en las otras funciones
        this.sound = plugin.configManager.get(nodePath, "sound");
        this.volume = plugin.configManager.getDouble(nodePath, "volume");
        this.pitch = plugin.configManager.getDouble(nodePath, "pitch");
        this.lightning = plugin.configManager.getBoolean(nodePath, "lightning");
        this.particle = plugin.configManager.get(nodePath, "particle");
        this.particleAmount = plugin.configManager.getInt(nodePath, "particle-amount");
        this.offsetX = plugin.configManager.getDouble(nodePath, "offset-x");
        this.offsetY = plugin.configManager.getDouble(nodePath, "offset-y");
        this.offsetZ = plugin.configManager.getDouble(nodePath, "offset-z");
        this.optionalValue = plugin.configManager.getDouble(nodePath, "optional-value");
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