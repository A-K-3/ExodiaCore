package net.gmip.core.utils.plugin.message;

import net.gmip.core.utils.plugin.PluginUtils;

public enum MessageType {

    /* Enumeración para gestionar los tipos de mensajes que se pueden enviar por consola */

    WARN(PluginUtils.PREFIX + "&8[&eWarn&8] &7"),
    DEBUG(PluginUtils.PREFIX + "&8[&2Debug&8] &7"),
    ERROR(PluginUtils.PREFIX + "&8[&cError&8] &7");

    private final String prefix;

    MessageType(String prefix) {
        this.prefix = prefix;
    }

    public String get() {
        return prefix;
    }
}
