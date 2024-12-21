package net.gmip.core.event.cancelpiston;

import net.gmip.core.event.ExodiaEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.List;

public class OnPistonEvent extends ExodiaEvent {
    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (shouldCancel(event.getBlocks())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (shouldCancel(event.getBlocks())) {
            event.setCancelled(true);
        }
    }
    private boolean shouldCancel(List<Block> blocks) {
        int slimeCount = 0;

        for (Block block : blocks) {
            if (block.getType() == Material.SLIME_BLOCK) {
                slimeCount++;
            }

            if (slimeCount > 1) {
                return true;
            }
        }
        return false;
    }
}
