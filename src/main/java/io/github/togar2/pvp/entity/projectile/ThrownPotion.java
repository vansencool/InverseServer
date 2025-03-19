package io.github.togar2.pvp.entity.projectile;

import io.github.togar2.pvp.feature.effect.EffectFeature;
import io.github.togar2.pvp.utils.EffectUtil;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.effects.Effects;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.item.ThrownPotionMeta;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.PotionContents;
import net.minestom.server.potion.Potion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ThrownPotion extends CustomEntityProjectile implements ItemHoldingProjectile {
	private final EffectFeature effectFeature;
	
	public ThrownPotion(@Nullable Entity shooter, EffectFeature effectFeature) {
		super(shooter, EntityType.POTION);
		this.effectFeature = effectFeature;
		
		// Why does Minestom have the wrong value 0.03 in its registries?
		setAerodynamics(getAerodynamics().withGravity(0.05));
	}
	
	@Override
	public boolean onHit(Entity entity) {
		splash(entity);
		return true;
	}
	
	@Override
	public boolean onStuck() {
		splash(null);
		return true;
	}
	
	public void splash(@Nullable Entity entity) {
		ItemStack item = getItem();
		
		PotionContents potionContents = item.get(ItemComponent.POTION_CONTENTS);
		List<Potion> potions = effectFeature.getAllPotions(potionContents);
		
		if (!potions.isEmpty()) {
			if (item.material() == Material.LINGERING_POTION) {
				//TODO lingering
			} else {
				applySplash(potionContents, entity);
			}
		}
		
		Pos position = getPosition();
		
		boolean instantEffect = false;
		for (Potion potion : potions) {
			if (potion.effect().registry().isInstantaneous()) {
				instantEffect = true;
				break;
			}
		}
		
		Effects effect = instantEffect ? Effects.INSTANT_SPLASH : Effects.SPLASH_POTION;
		EffectUtil.sendNearby(
				Objects.requireNonNull(getInstance()), effect, position.blockX(),
				position.blockY(), position.blockZ(), effectFeature.getPotionColor(potionContents),
				64.0, false
		);
	}
	
	private void applySplash(PotionContents potionContents, @Nullable Entity hitEntity) {
		BoundingBox boundingBox = getBoundingBox().expand(8.0, 4.0, 8.0);
		List<LivingEntity> entities = Objects.requireNonNull(getInstance()).getEntities().stream()
				.filter(entity -> boundingBox.intersectEntity(getPosition().add(0, -2, 0), entity))
				.filter(entity -> entity instanceof LivingEntity
						&& !(entity instanceof Player player && player.getGameMode() == GameMode.SPECTATOR))
				.map(entity -> (LivingEntity) entity).collect(Collectors.toList());
		
		if (hitEntity instanceof LivingEntity && !entities.contains(hitEntity))
			entities.add((LivingEntity) hitEntity);
		if (entities.isEmpty()) return;
		
		for (LivingEntity entity : entities) {
			if (entity.getEntityType() == EntityType.ARMOR_STAND) continue;
			
			double distanceSquared = getDistanceSquared(entity);
			if (distanceSquared >= 16.0) continue;
			
			double proximity = entity == hitEntity ? 1.0 : (1.0 - Math.sqrt(distanceSquared) / 4.0);
			effectFeature.addSplashPotionEffects(entity, potionContents, proximity, this, getShooter());
		}
	}
	
	@NotNull
	public ItemStack getItem() {
		return ((ThrownPotionMeta) getEntityMeta()).getItem();
	}
	
	@Override
	public void setItem(@NotNull ItemStack item) {
		((ThrownPotionMeta) getEntityMeta()).setItem(item);
	}
}
