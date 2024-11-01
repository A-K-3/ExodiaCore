package net.gmip.core.event.staffCommandEvents;

import net.gmip.core.GMIPCore;
import net.gmip.core.event.ExodiaEvent;
import net.gmip.core.utils.Staff; // Importa la clase Staff
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent; // Importa este evento
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class staffEvents extends ExodiaEvent implements Listener {
    private final GMIPCore plugin;
    private final Staff staff; // Instancia de Staff
    private final Set<UUID> frozenPlayers = new HashSet<>();
    private final Map<UUID, Long> lastInteraction = new HashMap<>();
    private final long cooldownTime = 500; // Cooldown de 500 ms

    public staffEvents(GMIPCore plugin, Staff staff) {
        this.plugin = plugin;
        this.staff = staff; // Inicializa la instancia de Staff
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        Entity clickedEntity = e.getRightClicked();

        // Verificar que el jugador haga clic derecho en otro jugador
        if (clickedEntity instanceof Player) {
            Player targetPlayer = (Player) clickedEntity;

            // Verificar que el jugador tenga el ítem específico en la mano
            if (itemInHand != null && itemInHand.getType() != Material.AIR && itemInHand.hasItemMeta()) {
                ItemMeta itemMeta = itemInHand.getItemMeta();

                // Verificar que el ítem tenga el identificador en PersistentDataContainer
                NamespacedKey key = new NamespacedKey(plugin, "freezeID");
                if (itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING) &&
                        "freeze".equals(itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING))) {

                    UUID targetUUID = targetPlayer.getUniqueId();

                    // Verificar el tiempo de cooldown
                    long currentTime = System.currentTimeMillis();
                    if (lastInteraction.containsKey(targetUUID) && (currentTime - lastInteraction.get(targetUUID)) < cooldownTime) {
                        return; // Sale si el cooldown no ha pasado
                    }

                    // Actualizar el tiempo de la última interacción
                    lastInteraction.put(targetUUID, currentTime);

                    // Toggle freeze state
                    if (frozenPlayers.contains(targetUUID)) {
                        frozenPlayers.remove(targetUUID);
                        player.sendMessage(ChatColor.RED + targetPlayer.getName() + " ha sido desfreezeado.");
                        targetPlayer.sendMessage(ChatColor.RED + "Has sido desfreezeado.");
                    } else {
                        frozenPlayers.add(targetUUID);
                        player.sendMessage(ChatColor.AQUA + targetPlayer.getName() + " ha sido freezeado.");
                        targetPlayer.sendMessage(ChatColor.RED + "Has sido freezeado.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // Cancelar movimiento si el jugador está en la lista de freezeados
        if (frozenPlayers.contains(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage(ChatColor.RED + "Estás freezeado, no te puedes mover!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        // Remover al jugador de la lista de freezeados al desconectarse
        UUID playerUUID = e.getPlayer().getUniqueId();
        if (frozenPlayers.contains(playerUUID)) {
            frozenPlayers.remove(playerUUID);
        }
    }


    //TODO: No funciona no sé por qué
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // Recuperar el inventario original al entrar
        UUID playerUUID = e.getPlayer().getUniqueId();

        System.out.println("HOLA?");
        if (staff.savedInventories.containsKey(playerUUID)) {
            e.getPlayer().getInventory().setContents(staff.savedInventories.get(playerUUID));
            staff.savedInventories.remove(playerUUID); // Eliminar el inventario original después de restaurarlo
        }
    }
}
