package net.vansen.events.entity;

import net.minestom.server.event.*;
import net.minestom.server.event.item.*;
import net.vansen.events.entity.sub.*;

public class EntityEvents {

    public static EventNode<?> get() {
        var eventNode = EventNode.type("entity-node", EventFilter.ENTITY);

        eventNode.addListener(PickupItemEvent.class, PickupItem::run);

        return eventNode;
    }
}
