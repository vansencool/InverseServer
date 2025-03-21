package net.vansen.events.entity.sub;

import net.minestom.server.entity.Player;
import net.minestom.server.event.item.PickupItemEvent;

public class PickupItem {

    public static void run(PickupItemEvent event) {
        var itemStack = event.getItemStack();
        if (event.getLivingEntity() instanceof Player player) {
            player.getInventory().addItemStack(itemStack);
        }
    }
}
