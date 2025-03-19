package io.github.togar2.pvp.enchantment.enchantments;

import io.github.togar2.pvp.enchantment.CombatEnchantment;
import io.github.togar2.pvp.enchantment.EntityGroup;
import io.github.togar2.pvp.feature.FeatureType;
import io.github.togar2.pvp.feature.config.FeatureConfiguration;
import io.github.togar2.pvp.feature.enchantment.EnchantmentFeature;
import io.github.togar2.pvp.utils.PotionFlags;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.registry.DynamicRegistry;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DamageEnchantment extends CombatEnchantment {
	private final Type type;
	
	public DamageEnchantment(DynamicRegistry.Key<Enchantment> enchantment, Type type, EquipmentSlot... slotTypes) {
		super(enchantment, Set.of(FeatureType.VERSION), slotTypes);
		this.type = type;
	}
	
	@Override
	public float getAttackDamage(int level, EntityGroup group,
	                             EnchantmentFeature feature, FeatureConfiguration configuration) {
		if (type == Type.ALL) {
			if (configuration.get(FeatureType.VERSION).legacy()) return level * 1.25F;
			return 1.0F + (float) Math.max(0, level - 1) * 0.5F;
		} else if (type == Type.UNDEAD && group == EntityGroup.UNDEAD) {
			return (float) level * 2.5F;
		} else {
			return type == Type.ARTHROPODS && group == EntityGroup.ARTHROPOD ? (float) level * 2.5F : 0.0F;
		}
	}
	
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level,
	                            EnchantmentFeature feature, FeatureConfiguration configuration) {
		if (target instanceof LivingEntity livingEntity) {
			if (type == Type.ARTHROPODS && EntityGroup.ofEntity(livingEntity) == EntityGroup.ARTHROPOD) {
				int i = 20 + ThreadLocalRandom.current().nextInt(10 * level);
				livingEntity.addEffect(new Potion(PotionEffect.SLOWNESS, (byte) 3, i, PotionFlags.defaultFlags()));
			}
		}
	}
	
	public enum Type {
		ALL, UNDEAD, ARTHROPODS
	}
}
