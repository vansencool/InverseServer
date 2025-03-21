package net.vansen;

import io.github.togar2.pvp.MinestomPvP;
import io.github.togar2.pvp.feature.CombatFeatures;
import io.github.togar2.pvp.feature.FeatureType;
import me.lucko.luckperms.common.config.generic.adapter.EnvironmentVariableConfigAdapter;
import me.lucko.luckperms.common.config.generic.adapter.MultiConfigurationAdapter;
import me.lucko.luckperms.minestom.CommandRegistry;
import me.lucko.luckperms.minestom.LuckPermsMinestom;
import me.lucko.spark.minestom.SparkMinestom;
import net.hollowcube.polar.PolarLoader;
import net.hollowcube.polar.PolarWorld;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.world.DimensionType;
import net.vansen.basic.CommandsRegistrar;
import net.vansen.config.Configuration;
import net.vansen.events.entity.EntityEvents;
import net.vansen.events.player.PlayerEvents;
import net.vansen.events.server.ServerEvents;
import net.vansen.fursconfig.FursConfig;
import net.vansen.generator.DesertGenerator;
import net.vansen.permissions.Permissions;
import net.vansen.tasks.Tasks;
import net.vansen.terminal.MinestomTerminal;
import net.vansen.util.Variables;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws IOException {
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer(MinecraftServer.getDimensionTypeRegistry().register(
                "completely_an_original_dimension_name", DimensionType.builder().ambientLight(2f).build()
        ));
        Variables.instanceContainer = instanceContainer;
        instanceContainer.setGenerator(new DesertGenerator());
        Path worldPath = Path.of("world/world.polar");
        if (!Files.exists(worldPath)) {
            Files.createDirectories(worldPath.getParent());
        }
        PolarLoader loader = new PolarLoader(worldPath).setParallel(false);
        loader.world().setCompression(PolarWorld.CompressionType.NONE);
        instanceContainer.setChunkLoader(loader);
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addChild(PlayerEvents.get());
        globalEventHandler.addChild(EntityEvents.get());
        globalEventHandler.addChild(ServerEvents.get());
        MinestomPvP.init();
        globalEventHandler.addChild(CombatFeatures.modernVanilla().createNode());
        instanceContainer.setExplosionSupplier(CombatFeatures.modernVanilla().get(FeatureType.EXPLOSION).getExplosionSupplier());

        Tasks.tasks();
        CommandsRegistrar.register();
        Variables.sparkPlatform = SparkMinestom.builder(Path.of("plugins/spark"))
                .commands(true)
                .permissionHandler((sender, permission) -> Permissions.SPARK.has(sender))
                .enable();
        Variables.luckperms = LuckPermsMinestom.builder(Path.of("plugins/luckperms"))
                .commandRegistry(CommandRegistry.minestom())
                .configurationAdapter(plugin -> new MultiConfigurationAdapter(plugin, new EnvironmentVariableConfigAdapter(plugin)))
                .permissionSuggestions()
                .dependencyManager(true)
                .enable();

        Path target = Path.of("config.conf");
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.conf");
        if (inputStream != null) {
            if (!Files.exists(target)) {
                Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        FursConfig config = FursConfig.createAndParseFile(target);
        int port = config.getInt("server.port");
        String ip = config.getString("server.ip");
        String name = config.getString("server.name");
        LoggerFactory.getLogger("Main").info("Starting {} on port: {}, at ip: {}", name, port, ip);
        MinecraftServer.setBrandName(name);
        minecraftServer.start(ip, config.getInt("server.port"));
        MinestomTerminal.start();
        Configuration.ALLOW_ANYONE_TO_USE_JAVASCRIPT = config.getBoolean("special_operator.javascript.allow_anybody_to_use_javascript");
        Configuration.PLAYERS_ALLOWED_TO_USE_JAVASCRIPT = config.getList("special_operator.javascript.players_that_can_execute", String.class);
    }
}