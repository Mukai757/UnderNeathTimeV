package underneathtimev.provider;

import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * @author AoXiang_Soar
 */

public class UTVProviders {
	public static void onGatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVGlobalLootModifierProvider>) output -> new UTVGlobalLootModifierProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVLootTableProvider>) output -> new UTVLootTableProvider(output, event.getLookupProvider()));
		event.getGenerator().addProvider(event.includeServer(),
				(DataProvider.Factory<UTVBlockTagProvider>) output -> new UTVBlockTagProvider(output, event.getLookupProvider()));
	}
}
