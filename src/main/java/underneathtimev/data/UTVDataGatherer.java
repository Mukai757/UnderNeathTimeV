package underneathtimev.data;

import java.util.Set;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import underneathtimev.UnderneathTimeV;
import underneathtimev.data.loot_table.UTVGlobalLootModifierProvider;
import underneathtimev.data.loot_table.UTVLootTableProvider;
import underneathtimev.data.tag.UTVBlockTagProvider;
import underneathtimev.data.world.UTVBiomeModifiers;
import underneathtimev.data.world.UTVConfiguredFeatures;
import underneathtimev.data.world.UTVPlacedFeatures;

/**
 * A class to gather all the datapacks
 * 
 * @author AoXiang_Soar
 */

public class UTVDataGatherer {
	
	public static void onGatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVGlobalLootModifierProvider>) output -> new UTVGlobalLootModifierProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVLootTableProvider>) output -> new UTVLootTableProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVBlockTagProvider>) output -> new UTVBlockTagProvider(output, event.getLookupProvider()));
		
		event.getGenerator().addProvider(event.includeServer(),
		        (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(),
		        		createBuilder(), Set.of(UnderneathTimeV.MOD_ID)));
	}
	
	/**
	 * Generate a RegistrySetBuilder. Any class that requires registration using RegistrySetBuilder should be registered here
	 */
	private static RegistrySetBuilder createBuilder() {
		return new RegistrySetBuilder()
				.add(Registries.CONFIGURED_FEATURE, UTVConfiguredFeatures::bootstrap)
				.add(Registries.PLACED_FEATURE, UTVPlacedFeatures::bootstrap)
				.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UTVBiomeModifiers::bootstrap)
		;
	}
}
