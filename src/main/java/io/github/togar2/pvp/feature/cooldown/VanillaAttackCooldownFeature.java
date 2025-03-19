package io.github.togar2.pvp.feature.cooldown;

import io.github.togar2.pvp.feature.*;
import io.github.togar2.pvp.feature.config.*;
import io.github.togar2.pvp.utils.*;
import net.minestom.server.entity.*;
import net.minestom.server.entity.attribute.*;
import net.minestom.server.event.*;
import net.minestom.server.event.player.*;
import net.minestom.server.event.trait.*;
import net.minestom.server.tag.*;
import net.minestom.server.utils.*;

/**
 * Vanilla implementation of {@link AttackCooldownFeature}
 */
public class VanillaAttackCooldownFeature implements AttackCooldownFeature, RegistrableFeature {
    public static final DefinedFeature<VanillaAttackCooldownFeature> DEFINED = new DefinedFeature<>(
            FeatureType.ATTACK_COOLDOWN, VanillaAttackCooldownFeature::new,
            FeatureType.VERSION
    );

    public static final Tag<Long> LAST_ATTACKED_TICKS = Tag.Long("lastAttackedTicks");

    private final FeatureConfiguration configuration;
    private CombatVersion version;

    public VanillaAttackCooldownFeature(FeatureConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void initDependencies() {
        this.version = configuration.get(FeatureType.VERSION);
    }

    @Override
    public void init(EventNode<EntityInstanceEvent> node) {
        node.addListener(EventListener.builder(PlayerHandAnimationEvent.class).handler(event ->
                resetCooldownProgress(event.getPlayer())).build());

        node.addListener(EventListener.builder(PlayerChangeHeldSlotEvent.class).handler(event -> {
            if (!event.getPlayer().getItemInMainHand()
                    .isSimilar(event.getPlayer().getInventory().getItemStack(event.getNewSlot()))) {
                resetCooldownProgress(event.getPlayer());
            }
        }).build());
    }

    @Override
    public void resetCooldownProgress(Player player) {
        player.setTag(LAST_ATTACKED_TICKS, player.getAliveTicks());
    }

    @Override
    public double getAttackCooldownProgress(Player player) {
        if (version.legacy()) return 1.0;

        Long lastAttacked = player.getTag(LAST_ATTACKED_TICKS);
        if (lastAttacked == null) return 1.0;

        long timeSinceLastAttacked = player.getAliveTicks() - lastAttacked;
        return MathUtils.clamp(
                (timeSinceLastAttacked + 0.5) / getAttackCooldownProgressPerTick(player),
                0, 1
        );
    }

    protected double getAttackCooldownProgressPerTick(Player player) {
        return (1 / player.getAttributeValue(Attribute.ATTACK_SPEED)) * 20;
    }
}
