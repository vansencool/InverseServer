package net.vansen.events.entity;

import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.item.PickupItemEvent;
import net.vansen.events.entity.sub.PickupItem;

public class EntityEvents {

    public static EventNode<?> get() {
        var eventNode = EventNode.type("entity-node", EventFilter.ENTITY);

        eventNode.addListener(PickupItemEvent.class, PickupItem::run);

        return eventNode;
    }
}
