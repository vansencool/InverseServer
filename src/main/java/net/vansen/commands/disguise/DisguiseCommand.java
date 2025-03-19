package net.vansen.commands.disguise;

import net.minestom.server.command.builder.Command;
import net.vansen.commands.disguise.sub.ResetDisguiseCommand;
import net.vansen.commands.disguise.sub.SetDisguiseCommand;

public class DisguiseCommand extends Command {

    public DisguiseCommand() {
        super("disguise");

        addSubcommand(new ResetDisguiseCommand());
        addSubcommand(new SetDisguiseCommand());
    }
}
