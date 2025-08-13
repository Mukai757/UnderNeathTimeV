package sfac.ut5.data;

import java.util.Set;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.data.loot_table.UTVGlobalLootModifierProvider;
import sfac.ut5.data.loot_table.UTVLootTableProvider;
import sfac.ut5.data.model.UTVBlockStateProvider;
import sfac.ut5.data.model.UTVItemModelProvider;
import sfac.ut5.data.tag.UTVBlockTagProvider;
import sfac.ut5.data.world.UTVBiomeModifiers;
import sfac.ut5.data.world.UTVConfiguredFeatures;
import sfac.ut5.data.world.UTVPlacedFeatures;

/**
 * A class to gather all the datapacks.</br>
 * All items and blocks have undergone default datagen processing. If special
 * data files are required, please modify the use of <code>if</code> in the
 * <code>for</code> loops within each Provider to annotate them
 * 
 * @author AoXiang_Soar
 */

public class UTVDataGatherer {
	
	public static void onGatherData(GatherDataEvent event) {
		// Server
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVGlobalLootModifierProvider>) output -> new UTVGlobalLootModifierProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVLootTableProvider>) output -> new UTVLootTableProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVBlockTagProvider>) output -> new UTVBlockTagProvider(output, event.getLookupProvider()));
		
		event.getGenerator().addProvider(event.includeServer(),
		        (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(),
		        		createBuilder(), Set.of(UnderneathTimeV.MOD_ID)));
		
		// Client
		event.getGenerator().addProvider(event.includeClient(),
				(DataProvider.Factory<UTVBlockStateProvider>) output -> new UTVBlockStateProvider(output, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeClient(),
				(DataProvider.Factory<UTVItemModelProvider>) output -> new UTVItemModelProvider(output, event.getExistingFileHelper()));
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
