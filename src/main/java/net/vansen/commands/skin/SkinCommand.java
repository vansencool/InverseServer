package net.vansen.commands.skin;

import net.minestom.server.command.builder.*;
import net.vansen.commands.skin.sub.*;

public class SkinCommand extends Command {

    public SkinCommand() {
        super("skin");

        addSubcommand(new ResetSubCommand());
        addSubcommand(new SetSubCommand());
    }
}