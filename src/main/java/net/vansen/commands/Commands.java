package net.vansen.commands;

import net.minestom.server.command.builder.Command;
import net.vansen.commands.admin.javascript.JavascriptCommand;
import net.vansen.commands.disguise.DisguiseCommand;
import net.vansen.commands.skin.SkinCommand;
import net.vansen.commands.staff.utils.*;
import net.vansen.commands.staff.utils.fly.FlyCommand;
import net.vansen.commands.staff.utils.fly.FlyingSpeedCommand;
import net.vansen.commands.staff.utils.gamemode.*;
import net.vansen.commands.staff.utils.teleport.StartTeleportingPlayersAroundRandomlyCommand;
import net.vansen.commands.staff.utils.teleport.StopTeleportingCommand;
import net.vansen.commands.staff.utils.teleport.TeleportCommand;

/**
 * List of commands
 */
public enum Commands {

    /**
     * Notify command.
     * <p>
     * Sends a notification to players.
     */
    NOTIFY(new NotifyCommand()),

    /**
     * Creative command.
     * <p>
     * Toggles creative mode for player.
     */
    CREATIVE(new CreativeCommand()),

    /**
     * Skin command.
     * <p>
     * Changes the player's skin.
     */
    SKIN(new SkinCommand()),

    /**
     * Start teleporting players around randomly command.
     * <p>
     * Teleports players to random locations.
     */
    START_TELEPORTING_PLAYERS_AROUND_RANDOMLY(new StartTeleportingPlayersAroundRandomlyCommand()),

    /**
     * Stop teleporting command.
     * <p>
     * Stops teleporting players.
     */
    STOP_TELEPORTING(new StopTeleportingCommand()),

    /**
     * Gamemode command.
     * <p>
     * Changes the game mode for the player.
     */
    GAMEMODE(new GamemodeCommand()),

    /**
     * Player list command.
     * <p>
     * Displays a list of online players.
     */
    PLAYER_LIST(new PlayerListCommand()),

    /**
     * Survival command.
     * <p>
     * Toggles survival mode for the player.
     */
    SURVIVAL(new SurvivalCommand()),

    /**
     * Spectator command.
     * <p>
     * Toggles spectator mode for the player.
     */
    SPECTATOR(new SpectatorCommand()),

    /**
     * Adventure command.
     * <p>
     * Toggles adventure mode for the player.
     */
    ADVENTURE(new AdventureCommand()),

    /**
     * Worlds command.
     * <p>
     * Lists available worlds.
     */
    WORLDS(new WorldsCommand()),

    /**
     * Fly command.
     * <p>
     * Toggles flying for players.
     */
    FLY(new FlyCommand()),

    /**
     * Flying speed command.
     * <p>
     * Changes the flying speed for players.
     */
    FLYING_SPEED(new FlyingSpeedCommand()),

    /**
     * Monitor command.
     * <p>
     * Monitors the server.
     */
    MONITOR(new MonitorCommand()),

    /**
     * Stop command.
     * <p>
     * Stops the server.
     */
    STOP(new StopCommand()),

    /**
     * Teleport command.
     * <p>
     * Teleports players to a specified location or player.
     */
    TELEPORT(new TeleportCommand()),

    /**
     * Threads command.
     * <p>
     * Displays information about threads currently running.
     */
    THREADS(new ThreadsCommand()),

    /**
     * Disguise command.
     * <p>
     * Disguises players as other entities.
     */
    DISGUISE(new DisguiseCommand()),

    /**
     * Admin command for running javascript code on the server, IT IS VERY DANGEROUS TO LET ANYONE USE THIS
     */
    JAVASCRIPT(new JavascriptCommand());

    public final Command command;

    Commands(Command command) {
        this.command = command;
    }
}