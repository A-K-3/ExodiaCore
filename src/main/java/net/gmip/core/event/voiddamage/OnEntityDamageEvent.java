package net.gmip.core.event.voiddamage;

import net.gmip.core.event.ExodiaEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnEntityDamageEvent extends ExodiaEvent {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            // If the player falls into the void, cancel the event and set the player's health to 0
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                e.setCancelled(true);
                player.setHealth(0);
            }
        }
    }
}
