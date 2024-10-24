package net.gmip.core.manager.config;

import net.gmip.core.GMIPCore;
import net.gmip.core.exceptions.PluginErrorException;
import net.gmip.core.utils.plugin.PluginUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class ConfigManager {

    /* Clase para gestionar la configuración del plugin. Esta clase actúa como caché sobre el archivo
     * de configuración, por lo tanto es lo más óptimo para no tener que leer siempre la config.
     * Le falta programar el getList()  */

    private final GMIPCore plugin;
    private final HashMap<String, HashMap<String, String>> configCache = new HashMap<>();
    private File file;
    private FileConfiguration config;


    /* Constructor de la clase, recibe la instancia del plugin y el nombre del archivo de configuración (opcional) */
    public ConfigManager(GMIPCore plugin, String fileName) {
        this.plugin = plugin;
        create(fileName);
    }

    public ConfigManager(GMIPCore plugin, String resourcePath, File @NotNull ... files) {
        this.plugin = plugin;
        create(resourcePath, files);
    }

    private void create(String fileName) {

        if (fileName == null) fileName = "config.yml";

        file = new File(plugin.getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    private void create(String resourcePath, File @NotNull ... files) {

        if (files[1] == null) files[1] = new File("config.yml");

        this.file = new File(files[0], files[1].getName());
        config = YamlConfiguration.loadConfiguration(file);

        saveResource(plugin, resourcePath, files[1], files[0]);
    }

    public String get(String path) {
        return get(path, "");
    }

    public String get(String path, String key) {
        String fullPath = key.isEmpty() ? path : path + "." + key;

        if (configCache.containsKey(fullPath)) {
            HashMap<String, String> map = configCache.get(fullPath);
            if (map.containsKey(key)) {
                return map.get(key);
            }

            configCache.put(fullPath, new HashMap<>() {{
                put(key, config.getString(fullPath, "N/A"));
            }});
        }

        String value = config.getString(fullPath, "N/A");
        byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
        value = new String(bytes, StandardCharsets.UTF_8);
        String finalValue = value;

        configCache.put(fullPath, new HashMap<>() {{
            put(key, finalValue);
        }});

        if (value.equals("N/A")) {
            PluginUtils.sendWarnMessage("The value " + fullPath + " is not set in the " + file.getName() + " file!");
        }

        return value;
    }

    public int getInt(String path, String key) {
        String value = get(path, key);

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new PluginErrorException("The value " + path + "." + key + " is not a number in the " + file.getName() + " file!", new IllegalArgumentException());
        }
    }

    public String get(String path, String key, String defaultValue) {
        String value = get(path, key);
        return value.equals("N/A") ? defaultValue : value;
    }

    public int getInt(String path, String key, int defaultValue) {
        try {
            return Integer.parseInt(get(path, key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double getDouble(String path, String key, double defaultValue) {
        try {
            return Double.parseDouble(get(path, key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String path, String key, boolean defaultValue) {
        String value = get(path, key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public double getDouble(String path, String key) {
        String value = get(path, key);

        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            System.err.println("Error al convertir '" + value + "' a double.");
            throw new PluginErrorException("The value " + path + "." + key + " is not a number in the " + file.getName() + " file!", new IllegalArgumentException());
        }
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, "");
    }

    public boolean getBoolean(String path, String key) {
        String value = get(path, key);

        if (value == null) {
            return false;
        }

        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new PluginErrorException("The value " + path + "." + key + " is not set as boolean in the " + file.getName() + " file!", new IllegalArgumentException());
        }

        return Boolean.parseBoolean(value);
    }

    public List<String> getList(String path, String key) {
        return config.getStringList(path + "." + key);
    }

    public ConfigurationSection getSection(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            PluginUtils.sendWarnMessage("The section " + path + " is not set in the " + file.getName() + " file!");
        }
        return section;
    }

    public ConfigurationSection getSection(ConfigurationSection parentSection, String path) {
        ConfigurationSection section = parentSection.getConfigurationSection(path);
        if (section == null) {
            PluginUtils.sendWarnMessage("The section " + path + " is not set in the " + file.getName() + " file!");
        }
        return section;
    }

    public Set<String> getKeys(ConfigurationSection section) {

        Set<String> keys = section.getKeys(false);

        if (keys.isEmpty()) {
            PluginUtils.sendWarnMessage("The section " + section.getName() + " is empty in the " + file.getName() + " file!");
        }

        return keys;
    }

    public void reload() {
        configCache.clear();
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void saveResource(JavaPlugin plugin, @NotNull String resourcePath, @NotNull File config, @NotNull File folder) {
        if (!resourcePath.isEmpty()) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = plugin.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + config);
            } else {
                File outFile = new File(folder, resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(folder, resourcePath.substring(0, Math.max(lastIndex, 0)));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (!outFile.exists()) {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    plugin.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }
}

