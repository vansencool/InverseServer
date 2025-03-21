package net.vansen.events.player.sub;

import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.item.ItemStack;
import net.vansen.util.Variables;

import java.time.Duration;

public class PlayerBlockBreak {

    public static void run(PlayerBlockBreakEvent event) {
        if (event.getBlock().registry().material() != null) {
            if (event.getPlayer().getGameMode().name().equals("CREATIVE")) {
                return;
            }
            var itemStack = ItemStack.of(event.getBlock().registry().material());
            ItemEntity itemEntity = new ItemEntity(itemStack);
            itemEntity.setInstance(Variables.instanceContainer, event.getBlockPosition().add(0.5, 0.5, 0.5));
            itemEntity.setPickupDelay(Duration.ofMillis(500));
        }
    }
}
