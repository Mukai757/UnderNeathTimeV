package sfac.ut5.data.loot_table;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public class UTVGlobalLootModifierProvider extends GlobalLootModifierProvider {
	public UTVGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, UnderneathTimeV.MOD_ID);
	}

	@Override
	protected void start() {
		add("chests/simple_dungeon",
				new AddTableLootModifier(new LootItemCondition[] { 
						LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/simple_dungeon")).build(),
						//LootItemRandomChanceCondition.randomChance(0.1f).build()
						},
						ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "chests/additional_dungeon_loot"))
					)
			);
	}

}
