package net.gmip.core.manager.config;

import net.gmip.core.GMIPCore;
import net.gmip.core.utils.plugin.PluginUtils;

public class MessagesManager extends ConfigManager {

    /* Clase para gestionar los mensajes del plugin
     * Solo añade un prefix al String de la config */

    private String path = "messages";

    public MessagesManager(GMIPCore plugin, String fileName) {
        super(plugin, fileName);
    }

    // Segundo constructor, por si el path no es "messages"
    public MessagesManager(GMIPCore plugin, String fileName, String path) {
        super(plugin, fileName);
        this.path = path;
    }


    // get(key, false) -> No añade el prefix
    // get(key) -> Añade el prefix
    // get(key, true) -> Añade el prefix
    public String get(PluginMessages key, Boolean... prefix) {
        return prefix.length != 0 && !prefix[0] ? get(this.path, key.get()) : PluginUtils.PREFIX + get(this.path, key.get());
    }

    public void reload() {
        super.reload();
    }
}