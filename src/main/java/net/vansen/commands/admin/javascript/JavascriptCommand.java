package net.vansen.commands.admin.javascript;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.vansen.commands.admin.javascript.bindings.ConsoleBinding;
import net.vansen.commands.admin.javascript.sub.FileCommand;
import net.vansen.commands.admin.javascript.sub.StringCommand;
import net.vansen.config.Configuration;
import net.vansen.permissions.Permissions;

import javax.script.*;

public class JavascriptCommand extends Command {
    public static ScriptEngineManager manager;

    static {
        try {
            manager = new ScriptEngineManager();
            ScriptEngineFactory factory = (ScriptEngineFactory) Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory").getDeclaredConstructor().newInstance();
            manager.registerEngineName("nashorn", factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JavascriptCommand() {
        super("javascript");

        setCondition(((sender, commandString) -> {
            if (!(sender instanceof Player player)) {
                return false;
            }
            if (Configuration.ALLOW_ANYONE_TO_USE_JAVASCRIPT) {
                return Permissions.OP.has(sender);
            }
            System.out.println(Configuration.PLAYERS_ALLOWED_TO_USE_JAVASCRIPT);
            System.out.println(player.getUsername());
            return Permissions.OP.has(sender) && Configuration.PLAYERS_ALLOWED_TO_USE_JAVASCRIPT.contains(player.getUsername());
        }));

        addSubcommand(new StringCommand());
        addSubcommand(new FileCommand());
    }

    public static void execute(Player player, String code) {
        try {
            ScriptEngine engine = manager.getEngineByName("nashorn");
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            bindings.clear();
            bindings.put("player", player);
            bindings.put("console", ConsoleBinding.INSTANCE);
            Object object = engine.eval(code);
            if (object == null) {
                player.sendMessage(Component.text("No result from that.", TextColor.fromHexString("#958aff")));
            }
            else {
                if (object instanceof Component component) {
                    player.sendMessage(component);
                }
                else {
                    player.sendMessage(Component.text("Result: (" + object.getClass().getPackage().getName() + "." + object.getClass().getSimpleName() + ") " + object, TextColor.fromHexString("#958aff")));
                }
            }
        } catch (ScriptException e) {
            player.sendMessage(Component.text("Error! " + e.getMessage(), TextColor.fromHexString("#ff6183")));
        }
    }
}
