package underneathtimev.provider.loot_table;

import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.item.UTVItems;

/**
 * @author AoXiang_Soar
 */

public class SimpleDungeonAdditionTable implements LootTableSubProvider {
	
	public SimpleDungeonAdditionTable(HolderLookup.Provider lookupProvider) {
        //super(lookupProvider);
    }
	
	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
		consumer.accept(ResourceKey.create(Registries.LOOT_TABLE, 
	            ResourceLocation.fromNamespaceAndPath(UnderNeathTimeV.MOD_ID, "chests/simple_dungeon")), LootTable.lootTable()
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                .withPool(LootPool.lootPool()
                		.setRolls(ConstantValue.exactly(1))
                		.setBonusRolls(ConstantValue.exactly(0))
                		.add(LootItem.lootTableItem(UTVItems.FATE_POCKET_WATCH))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
	}

}
