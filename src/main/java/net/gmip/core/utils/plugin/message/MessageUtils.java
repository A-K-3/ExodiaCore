package net.gmip.core.utils.plugin.message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class MessageUtils {

    /**
     * @param message The message to colorize.
     * @return The colorized message.
     */
    public static String colorize(String message) {
        message = TranslateHexColorCodes.translateHexColorCodes("&#", "", message);
        message = translateAlternateColorCodes('&', message);

        return message;
    }


    /**
     * Send a colorized message to the player.
     *
     * @param player  The player related.
     * @param message The message to send.
     */
    public static void send(Player player, String message) {
        if (message == null || message.isEmpty() || player == null) return;

        player.sendMessage(colorize(message));
    }

    /**
     * Send a colorized message to a command sender.
     *
     * @param sender  The command sender related.
     * @param message The message to send.
     */
    public static void send(CommandSender sender, String message) {
        if (message == null || message.isEmpty() || sender == null) return;

        sender.sendMessage(colorize(message));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player  The player related.
     * @param message The message to send.
     */
    public static void send(Player player, List<String> message) {
        if (message == null || message.isEmpty() || player == null) return;
        message.forEach(line -> send(player, line));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players The list of player's related.
     * @param message The message to send.
     */
    public static void send(Collection<Player> players, String message) {
        if (message == null || message.isEmpty() || players.isEmpty()) return;

        players.forEach(player -> send(player, message));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players The list of player's related.
     * @param message The message to send.
     */
    public static void send(Collection<Player> players, List<String> message) {
        if (message == null || message.isEmpty() || players.isEmpty()) return;

        players.forEach(player -> message.forEach(line -> send(player, line)));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player       The player related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Player player, String message, String[][] placeholders) {
        if (message == null || message.isEmpty() || player == null) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        send(player, colorize(message));
    }

    /**
     * Send a colorized message to the sender with placeholders.
     *
     * @param sender       The sender related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(CommandSender sender, String message, String[][] placeholders) {
        if (message == null || message.isEmpty() || sender == null) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        send(sender, colorize(message));
    }

    /**
     * Send a colorized message to the player.
     *
     * @param player       The player related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Player player, List<String> message, String[][] placeholders) {
        if (message == null || message.isEmpty() || player == null) return;

        for (String[] placeholder : placeholders) {
            message = message.stream().map(line -> line.replace(placeholder[0], placeholder[1])).collect(Collectors.toList());
        }

        message.forEach(line -> send(player, line));
    }

    /**
     * Send a colorized message to the command sender.
     *
     * @param sender       The sender of the command.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(CommandSender sender, List<String> message, String[][] placeholders) {
        if (message == null || message.isEmpty() || sender == null) return;

        if (placeholders != null) {
            for (String[] placeholder : placeholders) {
                message = message.stream().map(line -> line.replace(placeholder[0], placeholder[1])).collect(Collectors.toList());
            }
        }

        message.forEach(line -> send(sender, line));
    }

    /**
     * Send a colorized message to the player list.
     *
     * @param players      The players related.
     * @param message      The message to send.
     * @param placeholders The placeholders to replace.
     */
    public static void send(Collection<Player> players, String message, String[][] placeholders) {
        if (message == null || message.isEmpty()) return;

        for (String[] placeholder : placeholders) {
            message = message.replace(placeholder[0], placeholder[1]);
        }

        for (Player player : players) {
            send(player, message, placeholders);
        }
    }

}
