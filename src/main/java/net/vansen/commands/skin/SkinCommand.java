package net.vansen.commands.skin;

import net.minestom.server.command.builder.Command;
import net.vansen.commands.skin.sub.ResetSubCommand;
import net.vansen.commands.skin.sub.SetSubCommand;

public class SkinCommand extends Command {

    public SkinCommand() {
        super("skin");

        addSubcommand(new ResetSubCommand());
        addSubcommand(new SetSubCommand());
    }
}