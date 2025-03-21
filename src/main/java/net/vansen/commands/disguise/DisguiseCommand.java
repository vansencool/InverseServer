package net.vansen.commands.disguise;

import net.minestom.server.command.builder.Command;
import net.vansen.commands.disguise.sub.ResetDisguiseCommand;
import net.vansen.commands.disguise.sub.SetDisguiseCommand;
import net.vansen.permissions.Permissions;

public class DisguiseCommand extends Command {

    public DisguiseCommand() {
        super("disguise");

        setCondition(((sender, commandString) -> Permissions.DISGUISE.has(sender)));

        addSubcommand(new ResetDisguiseCommand());
        addSubcommand(new SetDisguiseCommand());
    }
}
