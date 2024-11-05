package net.gmip.core.utils;

import net.gmip.core.GMIPCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class Staff implements CommandExecutor {
    private final GMIPCore plugin;
    private final HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();

    public Staff(GMIPCore plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player usage");
            return true; // Cambiar a true para indicar que el comando se manejó
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (savedInventories.containsKey(uuid)) { // Comprueba si el uuid está ya en el hashmap
            // Recuperar el inventario guardado
            player.getInventory().setContents(savedInventories.get(uuid));
            savedInventories.remove(uuid);
        } else { // Aquí es donde se activa el StaffMode
            // Guardamos el inventario actual
            savedInventories.put(uuid, player.getInventory().getContents());

            // Limpiamos el inventario actual
            player.getInventory().clear();

            // Freeze ITEM
            ItemStack freeze = new ItemStack(Material.BLAZE_POWDER);
            ItemMeta freezeMeta = freeze.getItemMeta();
            freezeMeta.setDisplayName("FREEZE");

            NamespacedKey key = new NamespacedKey(plugin, "freezeID");
            freezeMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "freeze");
            freeze.setItemMeta(freezeMeta);

            // Coloca el objeto en la posición 1 (índice 0 para la posición 1)
            player.getInventory().setItem(0, freeze);
        }
        return true; // Indica que el comando se manejó correctamente
    }
}
