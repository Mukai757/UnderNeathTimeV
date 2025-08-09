package underneathtimev.world;

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
 * 世界生成绝对需要的绝对类（不懂）
 * 
 * @author Mukai
 */
public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_UT_ORE_KEY = registerKey("overwold_ut_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_UT_ORE_KEY = registerKey("nether_ut_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_UT_ORE_KEY = registerKey("end_ut_ore");


    //应该是矿物生成
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldBismuthOres = List.of(
                OreConfiguration.target(stoneReplaceables, UTVBlocks.TIME_SAND_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, UTVBlocks.TIME_SAND_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_UT_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBismuthOres, 9));
        register(context, END_UT_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                UTVBlocks.SPACE_DUST_ORE.get().defaultBlockState(), 9));
        register(context, END_UT_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                UTVBlocks.VOID_CRYSTAL_ORE.get().defaultBlockState(), 9));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(UnderNeathTimeV.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}