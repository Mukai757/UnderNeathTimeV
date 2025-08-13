package sfac.ut5.data.loot_table;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/**
 * @author AoXiang_Soar
 */

public class UTVLootTableProvider extends LootTableProvider {

	public UTVLootTableProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, Set.of(), List.of(
				new SubProviderEntry(SimpleDungeonAdditionTable::new, LootContextParamSets.CHEST),
				new SubProviderEntry(BlockLootProvider::new, LootContextParamSets.BLOCK)
				), registries);
	}
}
