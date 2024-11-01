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
    public HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>(); // Guardar inventario de StaffMode

    public Staff(GMIPCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player usage");
            return true;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (savedInventories.containsKey(uuid)) { // Recuperar inventario de StaffMode
            player.getInventory().setContents(savedInventories.get(uuid));
            savedInventories.remove(uuid); // Eliminar el inventario de StaffMode al recuperarlo
        } else { // Activar StaffMode y guardar el inventario original
            savedInventories.put(uuid, player.getInventory().getContents()); // Guardar inventario actual
            player.getInventory().clear();

            // Crear y dar ítem Freeze
            ItemStack freeze = new ItemStack(Material.BLAZE_ROD);
            ItemMeta freezeMeta = freeze.getItemMeta();
            freezeMeta.setDisplayName("§c§lFREEZE");
            NamespacedKey key = new NamespacedKey(plugin, "freezeID");
            freezeMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "freeze");
            freeze.setItemMeta(freezeMeta);
            player.getInventory().setItem(1, freeze);
        }
        return true;
    }
}
