package underneathtimev.data.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.block.UTVBlocks;

import java.util.List;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class UTVConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CHRONOSTICE_CRYSTAL_ORE_CONFIGURED_FEATURE = registerKey("chronostice_crystal_ore_configured_feature");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPACE_DUST_ORE_CONFIGURED_FEATURE = registerKey("space_dust_ore_configured_feature");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TIME_SAND_ORE_CONFIGURED_FEATURE = registerKey("time_sand_ore_configured_feature");
	public static final ResourceKey<ConfiguredFeature<?, ?>> VOID_CRYSTAL_ORE_CONFIGURED_FEATURE = registerKey("void_crystal_ore_configured_feature");

	private static final RuleTest STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	private static final RuleTest DEEPSLATE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
	private static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
	private static final RuleTest END_STONE = new BlockMatchTest(Blocks.END_STONE);

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		List<OreConfiguration.TargetBlockState> timeSandOres = List.of(
				OreConfiguration.target(STONE, UTVBlocks.TIME_SAND_ORE.get().defaultBlockState()),
				OreConfiguration.target(DEEPSLATE, UTVBlocks.TIME_SAND_ORE.get().defaultBlockState()));

		register(context, TIME_SAND_ORE_CONFIGURED_FEATURE, Feature.ORE, new OreConfiguration(timeSandOres, 9));
		register(context, CHRONOSTICE_CRYSTAL_ORE_CONFIGURED_FEATURE, Feature.ORE, 
				new OreConfiguration(DEEPSLATE, UTVBlocks.CHRONOSTICE_CRYSTAL_ORE.get().defaultBlockState(), 1));
		register(context, SPACE_DUST_ORE_CONFIGURED_FEATURE, Feature.ORE,
				new OreConfiguration(END_STONE, UTVBlocks.SPACE_DUST_ORE.get().defaultBlockState(), 9));
		register(context, VOID_CRYSTAL_ORE_CONFIGURED_FEATURE, Feature.ORE,
				new OreConfiguration(END_STONE, UTVBlocks.VOID_CRYSTAL_ORE.get().defaultBlockState(), 2));
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(UnderNeathTimeV.MOD_ID, name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
			ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}