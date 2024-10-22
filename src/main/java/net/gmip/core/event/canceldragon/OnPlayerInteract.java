package net.gmip.core.event.canceldragon;

import net.gmip.core.event.ExodiaEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnPlayerInteract extends ExodiaEvent {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.END_CRYSTAL) {
            Block block = event.getClickedBlock();
            if (block != null) {
                World world = block.getWorld();
                if (world.getEnvironment() == World.Environment.THE_END) {
                    Block blockClicked = event.getClickedBlock();
                    if (blockClicked != null && blockClicked.getType() == Material.BEDROCK) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

