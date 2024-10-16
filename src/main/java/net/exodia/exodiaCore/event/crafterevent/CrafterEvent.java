package net.exodia.exodiaCore.event.crafterevent;

import net.exodia.exodiaCore.event.ExodiaEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrafterEvent extends ExodiaEvent {

    @EventHandler
    public void onCrafterEvent(CrafterCraftEvent e) {
        ItemStack craftedItem = e.getRecipe().getResult();
        List<String> blockedItems = plugin.configManager.getList("", "crafter-blocker");
        if (!blockedItems.isEmpty() && blockedItems.contains(craftedItem.getType().name().toString())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraftingTableEvent(CraftItemEvent e) {
        ItemStack craftedItem = e.getRecipe().getResult();
        List<String> blockedItems = plugin.configManager.getList("", "crafter-blocker");
        if (!blockedItems.isEmpty() && blockedItems.contains(craftedItem.getType().name().toString())){
            Player player = (Player) e.getWhoClicked();
            player.sendMessage(ChatColor.RED + "No está permitido craftear " + craftedItem.getType().name().toString() + " dentro del servidor.");
            e.setCancelled(true);
        }
    }
}
