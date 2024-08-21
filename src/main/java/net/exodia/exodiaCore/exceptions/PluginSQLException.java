package net.exodia.exodiaCore.exceptions;

import net.exodia.exodiaCore.utils.Utils;
import net.exodia.exodiaCore.utils.plugin.PluginUtils;

import java.sql.SQLException;

public class PluginSQLException extends RuntimeException {

    public PluginSQLException(String message, SQLException e, String... ps) {
        super(null, null, true, false);
        String statement = "";

        if (ps.length > 0) {
            if (ps[0] != null) {
                statement = ps[0];
            }
        }

        PluginUtils.sendErrorMessage(message + "\n\n" +
                "&3Message: &7" + e.getMessage() + "\n" +
                "&3Cause: &7" + e.getCause() + "\n" +
                "&3SQLState: &7" + e.getSQLState() + "\n" +
                "&3ErrorCode: &7" + e.getErrorCode() + "\n" +
                "&3Statement: &7" + statement + "\n" +
                "&3StackTrace: \n&c" + Utils.cleanStackTree(e.getStackTrace()).replace("\n", "\n&c"));

    }
}
