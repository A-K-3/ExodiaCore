package net.exodia.exodiaCore.config;

public enum PluginMessages {

    /* Enumeración para gestionar los path's de la config y poder hacer un refactor mejor */

    // MESSAGES PATHS //
    PREFIX("prefix"),
    PLUGIN_RELOADED("plugin-reloaded"),
    MUST_BE_PLAYER("must-be-player"),
    YOURSELF("you-cant-use-yourself"),
    NO_PERMISSION("no-permission"),
    ENDER_CHEST_OPEN_TO_OTHER("ender-chest-open-to-other"),
    PLAYER_NOT_FOUND("player-not-found"),
    HELPOP_USAGE("helpop-usage"),
    HELPOP_FORMAT("helpop-format"),
    NO_PLAYERS_RANDOM_TELEPORT("no-players-random-teleport"),
    SUCCESSFUL_RANDOM_TELEPORT("successful-random-teleport"),
    SUCCESSFUL_TELEPORT("successfully-teleported-to-player"),
    ERROR_TELEPORT("error-teleporting-to-player"),
    INVENTORY_SEE_USAGE("inventory-see-usage"),
    STATS_USAGE("stats-usage"),
    CLEAR_REPORTS_USAGE("clear-reports-usage"),
    STATS_MESSAGE("stats-message"),
    RESET_STATS_USAGE("reset-stats-usage"),
    SUCCESSFUL_RESET_STATS("successfully-reset-stats"),
    SEEN_USAGE("seen-usage"),
    SEEN_MESSAGE("seen-message"),

    // CHAT MANAGEMENT //
    CHAT_ENABLED("chat-management.chat-enabled"),
    CHAT_DISABLED("chat-management.chat-disabled"),
    PLAYER_TRY_CHAT("chat-management.player-try-chat"),

    // BUILDER MODE //
    USAGE_BUILDER_MODE("builder-mode.usage"),
    YOU_CANT_ENABLE_BUILDER_MODE("builder-mode.you-cant-enable-builder-mode"),
    BUILDER_MODE_ENABLED("builder-mode.enabled"),
    BUILDER_MODE_DISABLED("builder-mode.disabled"),

    // FORCE MODE //

    NOT_IN_ANY_MODE("force-mode.not-in-any-mode"),
    NOT_IN_ANY_MODE_OTHER("force-mode.not-in-any-mode-other"),

    // CHANNEL TOGGLE //
    CHANNEL_TOGGLE_GLOBAL("channel-toggle.global-chat"),
    CHANNEL_TOGGLE_BUILDER("channel-toggle.builder-chat"),
    CHANNEL_TOGGLE_STAFF("channel-toggle.staff-chat"),
    CHANNEL_TOGGLE_ADMIN("channel-toggle.admin-chat"),
    CHANNEL_TOGGLE_MANAGEMENT("channel-toggle.management-chat"),

    // REPORT SYSTEM //
    REPORT_USAGE("report.usage"),
    REPORT_COOLDOWN("report.in-cooldown"),
    SUCCESSFUL_REPORTED("report.successfully-reported"),
    PLAYER_DONT_HAVE_REPORTS("report.player-dont-have-reports"),
    SUCCESSFUL_DELETED_REPORT("report.successfully-deleted-report"),
    SUCCESSFUL_CLEARED_REPORTED("report.successfully-cleared-reports"),

    // TICKET SYSTEM //
    TICKET_HELP_MESSAGE("ticket.help"),
    TICKET_USAGE("ticket.usage"),
    TICKET_CREATED("ticket.successfully-created"),
    TICKET_NOT_FOUND("ticket.not-found"),
    TICKETS_EMPTY("ticket.no-tickets-found"),
    TICKET_DELETED("ticket.successfully-deleted"),
    TICKET_ALREADY_CLOSED("ticket.already-closed"),
    TICKET_CLOSED("ticket.successfully-closed"),
    NO_SUCCESS_DUE_TO_TICKET_CLOSED("ticket.no-success-due-to-ticket-closed"),
    TICKET_REOPENED("ticket.successfully-reopened"),
    TICKET_ALREADY_OPEN("ticket.already-opened"),
    TICKET_INFO("ticket.information"),
    TICKET_REPLIED("ticket.successfully-replied"),
    TICKET_PRIORITY_CHANGED("ticket.successfully-changed-priority"),
    TICKET_ASSIGNED("ticket.successfully-assigned"),
    TICKET_UNASSIGNED("ticket.successfully-unassigned"),
    TICKET_CLAIMED("ticket.successfully-claimed"),
    TICKET_NO_ASSIGNED_TO_YOU("ticket.no-assigned-to-you"),
    TICKET_UNCLAIMED("ticket.successfully-unclaimed"),
    PENDING_TICKETS("ticket.pending-tickets"),
    NO_ASSIGNED_YET("ticket.no-assigned-yet"),
    NEW_TICKET_REPLY("ticket.new-ticket-reply"),

    // CLEAR CHAT //
    USAGE_CLEAR_CHAT("staff.clear-chat.usage"),
    SUCCESSFUL_CLEARED("staff.clear-chat.successfully-cleared"),
    GLOBAL_CHAT_CLEARED("staff.clear-chat.global-cleared-message"),

    // MODERATOR MODE //
    USAGE_MODERATOR_MODE("staff.moderator-mode.usage"),
    YOU_CANT_ENABLE_MODERATOR_MODE("staff.moderator-mode.you-cant-enable-moderator-mode"),
    MODERATOR_MODE_ENABLED("staff.moderator-mode.enabled"),
    MODERATOR_MODE_DISABLED("staff.moderator-mode.disabled"),

    // ALTS COMMAND //
    ALTS_USAGE("staff.alts.usage"),
    NO_ALTS_FOUND("staff.alts.no-alts-found"),
    ALTS_FOUND("staff.alts.alts-found"),

    // HISTORY //
    HISTORY_USAGE("staff.history.usage"),

    // CLEAR HISTORY //
    CLEAR_HISTORY_USAGE("staff.clear-history.usage"),
    SUCCESSFUL_CLEAR_HISTORY("staff.clear-history.successfully-cleared"),

    // FREEZE MODULE //
    FREEZE_USAGE("staff.freeze.usage"),
    SUCCESSFUL_FROZEN("staff.freeze.successfully-frozen"),
    SUCCESSFUL_UNFROZEN("staff.freeze.successfully-unfrozen"),
    YOU_HAVE_BEEN_FROZEN("staff.freeze.you-have-been-frozen"),
    YOU_HAVE_BEEN_UNFROZEN("staff.freeze.you-have-been-unfrozen"),
    YOU_ARE_FROZEN("staff.freeze.you-are-frozen"),
    HOLOGRAM_FROZEN("staff.freeze.hologram-frozen"),

    // VANISH MODULE //
    SUCCESSFUL_VANISHED("staff.vanish.successfully-vanished"),
    SUCCESSFUL_UNVANISHED("staff.vanish.successfully-unvanished"),

    // BLACKLIST //
    BLACKLIST_USAGE("staff.blacklist.usage"),
    PLAYER_ALREADY_BLACKLISTED("staff.blacklist.player-already-blacklisted"),
    PLAYER_BLACKLISTED("staff.blacklist.successfully-blacklisted"),

    CHECK_USAGE("staff.check.usage"),
    PUNISHMENTS_NOT_FOUND("staff.check.punishments-not-found"),
    CHECK_BANNED_FORMAT("staff.check.banned-format"),
    CHECK_MUTED_FORMAT("staff.check.muted-format"),

    // BAN COMMAND //
    BAN_USAGE("punishment.ban.usage"),
    REBAN_USAGE("punishment.re-ban.usage"),
    PLAYER_ALREADY_BANNED("punishment.ban.player-already-banned"),
    SUCCESSFUL_BANNED("punishment.ban.successfully-banned"),
    PLAYER_BANNED("punishment.ban.player-banned"),
    PLAYER_BANNED_SCREEN("punishment.ban.player-banned-screen"),
    PLAYER_TRY_TO_JOIN("punishment.ban.player-try-to-join"),
    GLOBAL_BAN_MESSAGE("punishment.ban.global-ban-message"),

    // UNBAN COMMAND //
    UNBAN_USAGE("punishment.unban.usage"),
    PLAYER_NOT_BANNED("punishment.unban.player-not-banned"),
    SUCCESSFUL_UNBANNED("punishment.unban.successfully-unbanned"),
    GLOBAL_UNBAN_MESSAGE("punishment.unban.global-unban-message"),

    // KICK COMMAND //
    KICK_USAGE("punishment.kick.usage"),
    DEFAULT_KICK_REASON("punishment.kick.default-reason"),
    SUCCESSFUL_KICKED("punishment.kick.successfully-kicked"),
    PLAYER_KICKED("punishment.kick.player-kicked"),
    PLAYER_KICKED_SCREEN("punishment.kick.player-kicked-screen"),
    GLOBAL_KICK_MESSAGE("punishment.kick.global-kick-message"),

    // MUTE COMMAND //
    MUTE_USAGE("punishment.mute.usage"),
    REMUTE_USAGE("punishment.re-mute.usage"),
    PLAYER_ALREADY_MUTED("punishment.mute.player-already-muted"),
    SUCCESSFUL_MUTED("punishment.mute.successfully-muted"),
    PLAYER_MUTED("punishment.mute.player-muted"),
    PLAYER_TRY_TO_CHAT("punishment.mute.player-try-to-chat"),
    PLAYER_TRY_TO_USE_COMMAND("punishment.mute.player-try-to-use-command"),
    STAFF_NOTIFY("punishment.mute.staff-notify"),
    GLOBAL_MUTE_MESSAGE("punishment.mute.global-mute-message"),

    // UNMUTE COMMAND //
    UNMUTE_USAGE("punishment.unmute.usage"),
    PLAYER_NOT_MUTED("punishment.unmute.player-not-muted"),
    SUCCESSFUL_UNMUTED("punishment.unmute.successfully-unmuted"),
    PLAYER_UNMUTED("punishment.unmute.player-unmuted"),
    GLOBAL_UNMUTE_MESSAGE("punishment.unmute.global-unmute-message"),

    // WARN COMMAND //
    WARN_USAGE("punishment.warn.usage"),
    SUCCESSFUL_WARNED("punishment.warn.successfully-warned"),
    PLAYER_WARNED("punishment.warn.player-warned"),
    GLOBAL_WARN_MESSAGE("punishment.warn.global-warn-message");

    private final String prefix;

    PluginMessages(String prefix) {
        this.prefix = prefix;
    }

    public String get() {
        return prefix;
    }
}