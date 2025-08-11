package underneathtimev.data.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import underneathtimev.UnderNeathTimeV;

import java.util.List;

/**
 * Handle placed features. A feature is used to decide what operation to do for a biome
 * 
 * @author Mukai
 * @author AoXiang_Soar
 */
public class UTVPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CHRONOSTICE_CRYSTAL_ORE_FEATURE = registerKey("chronostice_crystal_ore_feature");
    public static final ResourceKey<PlacedFeature> SPACE_DUST_ORE_FEATURE = registerKey("space_dust_ore_feature");
    public static final ResourceKey<PlacedFeature> TIME_SAND_ORE_FEATURE = registerKey("time_sand_ore_feature");
    public static final ResourceKey<PlacedFeature> VOID_CRYSTAL_ORE_FEATURE = registerKey("void_crystal_ore_feature");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // The ConfiguredFeature used to configure specific settings, such as replacement blocks
        register(context, CHRONOSTICE_CRYSTAL_ORE_FEATURE, configuredFeatures.getOrThrow(UTVConfiguredFeatures.CHRONOSTICE_CRYSTAL_ORE_CONFIGURED_FEATURE),
                OrePlacement.commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP)));
        register(context, SPACE_DUST_ORE_FEATURE, configuredFeatures.getOrThrow(UTVConfiguredFeatures.SPACE_DUST_ORE_CONFIGURED_FEATURE),
                OrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.TOP)));
        register(context, TIME_SAND_ORE_FEATURE, configuredFeatures.getOrThrow(UTVConfiguredFeatures.TIME_SAND_ORE_CONFIGURED_FEATURE),
                OrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(32))));
        register(context, VOID_CRYSTAL_ORE_FEATURE, configuredFeatures.getOrThrow(UTVConfiguredFeatures.VOID_CRYSTAL_ORE_CONFIGURED_FEATURE),
                OrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(32))));
        // TODO 让虚空晶只暴露在空气中
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(UnderNeathTimeV.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}