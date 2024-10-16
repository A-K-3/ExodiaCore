package net.exodia.exodiaCore.event.explosion;

import net.exodia.exodiaCore.event.ExodiaEvent;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;


public class ExplosionEvent extends ExodiaEvent {
    @EventHandler
    public void onEntityExplote(EntityExplodeEvent e){
        World world = e.getLocation().getWorld();
        List<String> worlds = plugin.configManager.getList("", "no-explosion-worlds");
        if (world != null && !worlds.isEmpty() && worlds.contains(world.getName())) {
            e.blockList().clear();
        }
    }
    @EventHandler
    public void onItemExplote(BlockExplodeEvent e){
        World world = e.getBlock().getWorld();
        List<String> worlds = plugin.configManager.getList("", "no-explosion-worlds");
        if (world != null && !worlds.isEmpty() && worlds.contains(world.getName())) {
            e.blockList().clear();
        }
    }
}
