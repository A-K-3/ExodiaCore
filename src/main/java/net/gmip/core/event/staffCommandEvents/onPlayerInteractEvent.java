package net.gmip.core.event.staffCommandEvents;

import net.gmip.core.event.ExodiaEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class onPlayerInteractEvent extends ExodiaEvent implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        // Verificar que el jugador haga clic derecho
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.hasItemMeta()) {
                ItemMeta itemMeta = itemInHand.getItemMeta();
                // Verificar que el ítem tenga el identificador en PersistentDataContainer
                NamespacedKey key = new NamespacedKey(plugin, "freezeID");
                if (itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING) && itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals("freeze")) {
                    //TODO: Aquí es donde iría el toggle para el PlayerMoveEvent cancelándolo
                    player.sendMessage(ChatColor.AQUA + "¡El usuario ha sido freezeado!");
                }
            }
        }
    }
}
