package net.vansen.events.player.sub;

import net.minestom.server.entity.*;
import net.minestom.server.event.item.*;
import net.vansen.util.*;

import java.time.*;

public class ItemDrop {

    public static void run(ItemDropEvent event) {
        ItemEntity itemEntity = new ItemEntity(event.getItemStack());
        itemEntity.setInstance(Variables.instanceContainer, event.getPlayer().getPosition());
        itemEntity.setVelocity(event.getPlayer().getPosition().direction().mul(10));
        itemEntity.setPickupDelay(Duration.ofMillis(500));

    }
}
