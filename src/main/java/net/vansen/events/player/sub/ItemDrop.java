package net.vansen.events.player.sub;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.timer.TaskSchedule;
import net.vansen.util.Variables;

import java.time.Duration;

public class ItemDrop {

    public static void run(ItemDropEvent event) {
        ItemEntity itemEntity = new ItemEntity(event.getItemStack());
        itemEntity.setInstance(Variables.instanceContainer, event.getPlayer().getPosition());
        itemEntity.setVelocity(event.getPlayer().getPosition().direction().mul(10));
        itemEntity.setPickupDelay(Duration.ofMillis(500));
        MinecraftServer.getSchedulerManager().buildTask(itemEntity::remove).delay(TaskSchedule.minutes(3)).schedule();
    }
}
