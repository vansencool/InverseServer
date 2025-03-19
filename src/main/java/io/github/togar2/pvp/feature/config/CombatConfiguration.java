package io.github.togar2.pvp.feature.config;

import io.github.togar2.pvp.feature.CombatFeature;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import io.github.togar2.pvp.feature.FeatureType;
import io.github.togar2.pvp.feature.provider.DifficultyProvider;
import io.github.togar2.pvp.utils.CombatVersion;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A configuration of combat features which can be used to easily resolve dependencies to other combat features.
 * <p>
 * Whereas {@link CombatFeatureSet} contains the full set of feature instances with resolved dependencies,
 * this class can be used to add {@link DefinedFeature} instances (features which haven't yet been instantiated).
 * Vanilla versions of these defined features can be found in {@link CombatFeatures}.
 * <p>
 * This class contains methods to easily configure the combat version and difficulty provider,
 * see {@link CombatConfiguration#version(CombatVersion)} and {@link CombatConfiguration#difficulty(DifficultyProvider)}.
 * <p>
 * When calling {@link CombatConfiguration#build()}, it resolves all the dependencies
 * and turns this configuration into a {@link CombatFeatureSet}.
 */
public class CombatConfiguration {
	private final Map<FeatureType<?>, ConstructableFeature> features = new HashMap<>();
	
	public final CombatConfiguration addAll(Collection<DefinedFeature<?>> constructors) {
		for (DefinedFeature<?> constructor : constructors) {
			add(constructor);
		}
		return this;
	}
	
	public final CombatConfiguration addAll(DefinedFeature<?>... constructors) {
		for (DefinedFeature<?> constructor : constructors) {
			add(constructor);
		}
		return this;
	}
	
	public CombatConfiguration version(CombatVersion version) {
		return add(FeatureType.VERSION, version);
	}
	
	/**
	 * @deprecated use {@link #version(CombatVersion)} instead
	 */
	@Deprecated
	public CombatConfiguration legacy(boolean legacy) {
		return version(CombatVersion.fromLegacy(legacy));
	}
	
	public CombatConfiguration difficulty(DifficultyProvider difficulty) {
		return add(FeatureType.DIFFICULTY, difficulty);
	}
	
	/**
	 * Adds a feature to the configuration. This will overwrite any existing feature of this type.
	 *
	 * @param feature the feature to add
	 * @return this
	 */
	public CombatConfiguration add(ConstructableFeature feature) {
		features.put(feature.type, feature);
		return this;
	}
	
	/**
	 * Adds a feature to the configuration. This will overwrite any existing feature of this type.
	 *
	 * @param type the type of the feature
	 * @param feature the feature to add
	 * @return this
	 */
	public CombatConfiguration add(FeatureType<?> type, CombatFeature feature) {
		return add(wrap(type, feature));
	}
	
	/**
	 * Adds a feature to the configuration. This will overwrite any existing feature of this type.
	 * Any dependencies the feature might have will first be looked up inside the override configuration.
	 *
	 * @param constructor the defined feature
	 * @param override the override configuration
	 * @return this
	 */
	public CombatConfiguration add(DefinedFeature<?> constructor, FeatureConfiguration override) {
		return add(wrap(constructor, override));
	}
	
	/**
	 * Adds a feature to the configuration. This will overwrite any existing feature of this type.
	 * Any dependencies the feature might have will first be looked up inside the override configuration.
	 *
	 * @param constructor the type of the feature
	 * @param override list of features to override the configuration of the base feature
	 * @return this
	 */
	public CombatConfiguration add(DefinedFeature<?> constructor, DefinedFeature<?>... override) {
		return add(wrap(constructor, override));
	}
	
	public CombatConfiguration remove(FeatureType<?> type) {
		features.remove(type);
		return this;
	}
	
	public static ConstructableFeature wrap(FeatureType<?> type, CombatFeature feature) {
		return new ConstructedFeature(type, feature);
	}
	
	public static ConstructableFeature wrap(DefinedFeature<?> constructor, FeatureConfiguration override) {
		Set<ConstructableFeature> overrideSet = new HashSet<>();
		override.forEach((k, v) -> overrideSet.add(wrap(k, v)));
		return wrap(constructor, overrideSet);
	}
	
	public static ConstructableFeature wrap(DefinedFeature<?> constructor, DefinedFeature<?>... override) {
		Set<ConstructableFeature> overrideSet = new HashSet<>();
		for (DefinedFeature<?> overrideFeature : override) {
			overrideSet.add(wrap(overrideFeature));
		}
		
		return wrap(constructor, overrideSet);
	}
	
	public static ConstructableFeature wrap(DefinedFeature<?> constructor, Set<ConstructableFeature> override) {
		Map<FeatureType<?>, ConstructableFeature> overrideMap = new HashMap<>();
		
		for (ConstructableFeature overrideFeature : override) {
			overrideMap.put(overrideFeature.type, overrideFeature);
			
			if (!constructor.dependencies().contains(overrideFeature.type))
				throw new RuntimeException("Feature " + constructor.featureType().name()
						+ " does not require a " + overrideFeature.type.name() + " feature");
		}
		
		return new LazyFeatureInit(constructor, overrideMap);
	}
	
	/**
	 * Resolves all the dependencies and turns this configuration into a {@link CombatFeatureSet}.
	 *
	 * @return the combat feature set
	 */
	public CombatFeatureSet build() {
		CombatFeatureSet result = new CombatFeatureSet();
		
		//List<ConstructableFeature> buildOrder = getBuildOrder();
		
		for (ConstructableFeature feature : features.values()) {
			CombatFeature currentResult = feature.construct(result);
			result.add(feature.type, currentResult);
		}
		
		result.initDependencies();
		
		return result;
	}
	
	/**
	 * Performs a (recursive) topological sort to make sure all the features
	 * that are depended on by other features are first in the list
	 *
	 * @return the list with ordering
	 */
	private List<ConstructableFeature> getBuildOrder() {
		List<ConstructableFeature> order = new ArrayList<>(features.size());
		Set<ConstructableFeature> visiting = new HashSet<>();
		
		while (true) {
			// Find unprocessed feature
			ConstructableFeature unprocessed = null;
			for (ConstructableFeature feature : features.values()) {
				if (order.contains(feature)) continue;
				if (visiting.contains(feature)) continue;
				unprocessed = feature;
			}
			
			if (unprocessed == null) break;
			visit(order, visiting, unprocessed);
		}
		
		return order;
	}
	
	private void visit(List<ConstructableFeature> order, Set<ConstructableFeature> visiting, ConstructableFeature current) {
		if (order.contains(current)) return; // Feature has already been added
		if (visiting.contains(current))
			throw new RuntimeException("Configuration has a recursive dependency");
		
		if (current instanceof LazyFeatureInit lazy) {
			visiting.add(current);
			
			for (FeatureType<?> dependType : lazy.constructor.dependencies()) {
				ConstructableFeature dependFeature = lazy.getOverrideOf(dependType);
				if (dependFeature == null) dependFeature = getFeatureOf(dependType);
				if (dependFeature != null) visit(order, visiting, dependFeature);
			}
			
			visiting.remove(current);
		}
		
		order.add(current);
	}
	
	private @Nullable ConstructableFeature getFeatureOf(FeatureType<?> type) {
		return features.get(type);
	}
	
	public sealed abstract static class ConstructableFeature {
		private final FeatureType<?> type;
		
		public ConstructableFeature(FeatureType<?> type) {
			this.type = type;
		}
		
		abstract CombatFeature construct(FeatureConfiguration configuration);
	}
	
	private static final class ConstructedFeature extends ConstructableFeature {
		private final CombatFeature feature;
		
		private ConstructedFeature(FeatureType<?> type, CombatFeature feature) {
			super(type);
			this.feature = feature;
		}
		
		@Override
		CombatFeature construct(FeatureConfiguration configuration) {
			return feature;
		}
	}
	
	private static final class LazyFeatureInit extends ConstructableFeature {
		private final DefinedFeature<?> constructor;
		private final Map<FeatureType<?>, ConstructableFeature> override;
		
		public LazyFeatureInit(DefinedFeature<?> constructor, Map<FeatureType<?>, ConstructableFeature> override) {
			super(constructor.featureType());
			this.constructor = constructor;
			this.override = override;
		}
		
		public @Nullable ConstructableFeature getOverrideOf(FeatureType<?> featureType) {
			return override.get(featureType);
		}
		
		@Override
		CombatFeature construct(FeatureConfiguration configuration) {
			FeatureConfiguration local = configuration.overlay();
			override.forEach((k, v) -> local.add(k, v.construct(configuration)));
			
			return constructor.construct(local);
		}
	}
}
