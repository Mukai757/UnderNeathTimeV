package underneathtimev.data.world;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import underneathtimev.UnderNeathTimeV;

/**
 * Handle biome modifiers. A modifier is used to decide which biome to modify
 * 
 * @author Mukai
 * @author AoXiang_Soar
 */
public class UTVBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_FEATURES_MODIFIER = registerKey("add_overworld_features_modifier");
    public static final ResourceKey<BiomeModifier> ADD_END_FEATURES_MODIFIER = registerKey("add_end_features_modifier");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomesToAdd = context.lookup(Registries.BIOME);
        
        // Modifiers must using features to decide what operation to do for a biome.
        context.register(ADD_OVERWORLD_FEATURES_MODIFIER, new AddFeaturesBiomeModifier(
        		biomesToAdd.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(UTVPlacedFeatures.CHRONOSTICE_CRYSTAL_ORE_FEATURE),
                		placedFeatures.getOrThrow(UTVPlacedFeatures.TIME_SAND_ORE_FEATURE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_END_FEATURES_MODIFIER, new AddFeaturesBiomeModifier(
                biomesToAdd.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(UTVPlacedFeatures.VOID_CRYSTAL_ORE_FEATURE),
                		placedFeatures.getOrThrow(UTVPlacedFeatures.SPACE_DUST_ORE_FEATURE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(UnderNeathTimeV.MOD_ID, name));
    }
}