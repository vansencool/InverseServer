package net.vansen.permissions;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import net.vansen.util.Variables;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public enum Permissions {

    /**
     * OP, operator, has access to ALL
     */
    OP("*"),

    /**
     * Monitor based commands, like /notify or /monitor
     */
    MONITOR("staff.utils.monitor"),

    /**
     * Teleport command
     */
    TELEPORT("staff.utils.teleport"),

    SPARK("staff.utils.spark"),

    FLY("staff.utils.fly"),

    RANDOM_TELEPORT("staff.utils.randomteleport"),

    GAMEMODE("staff.utils.gamemode"),

    WORLDS("staff.utils.worlds"),

    CHAT_BYPASS("server.chatbypass");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public boolean has(@NotNull CommandSender sender) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        try {
            return Objects.requireNonNull(Variables.luckperms.getUserManager().getUser(player.getUuid())).getCachedData().getPermissionData().checkPermission(this.permission).asBoolean();
        } catch (Exception e) {
            Logger.getLogger("Permissions").warning("Failed to check permission: " + this.permission + " for player: " + player.getName());
            return false;
        }
    }
}