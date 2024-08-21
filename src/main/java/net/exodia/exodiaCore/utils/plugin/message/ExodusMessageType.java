package net.exodia.exodiaCore.utils.plugin.message;

import net.exodia.exodiaCore.utils.Utils;

public enum ExodusMessageType {

    /* Enumeración para gestionar los tipos de mensajes que se pueden enviar por consola */

    WARN(Utils.PREFIX + "&8[&eWarn&8] &7"),
    DEBUG(Utils.PREFIX + "&8[&2Debug&8] &7"),
    ERROR(Utils.PREFIX + "&8[&cError&8] &7");

    private final String prefix;

    ExodusMessageType(String prefix) {
        this.prefix = prefix;
    }

    public String get() {
        return prefix;
    }
}
