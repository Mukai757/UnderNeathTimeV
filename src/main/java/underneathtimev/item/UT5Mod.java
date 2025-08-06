package underneathtimev.item;

import com.google.common.collect.Iterables;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(UT5Mod.MODID)
public class UT5Mod {
    public static final String MODID = "ut5";

    // 创建注册器
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 注册时之翼物品
    public static final DeferredItem<TimeWingsItem> TIME_WINGS = ITEMS.registerItem(
            "time_wings",
            TimeWingsItem::new,
            new Item.Properties().durability(432)
    );
    // 宿命怀表物品注册
    public static final DeferredItem<FateHourglassItem> FATE_HOURGLASS = ITEMS.registerItem(
            "fate_hourglass",
            FateHourglassItem::new,
            new Item.Properties().stacksTo(1) // 只能持有一个
    );


    public UT5Mod(IEventBus modEventBus) {
        // 注册物品和标签页
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        // 向 Data Generator 添加 DataProvider
        modEventBus.addListener(UT5Mod::onGatherData);
        // 注册事件监听器（NeoForge推荐方式）
        NeoForge.EVENT_BUS.addListener(UT5Mod::onLivingJump);
    }
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        // 需要获取注册表查询器
        var lookupProvider = event.getLookupProvider();
        // 添加战利品表的 DataProvider
        gen.addProvider(event.includeServer(), new LootProvider(packOutput, lookupProvider));
    }
    // 战利品表
    public static class LootProvider extends LootTableProvider {
        public LootProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> lookup) {
            super(gen, Set.of(), List.of(new SubProviderEntry(CustomBlockLoot::new, LootContextParamSets.BLOCK)), lookup);
        }

        @Override
        protected void validate(WritableRegistry<LootTable> registry, ValidationContext context, ProblemReporter.Collector collector) {
            // FIXME 需要核实正确写法
            // map.forEach((key, value) -> LootTables.validate(context, key, value));
        }
    }
    // 方块的战利品表
    public static class CustomBlockLoot extends BlockLootSubProvider {
        protected CustomBlockLoot(HolderLookup.Provider lookupProvider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
        }

        @Override
        protected void generate() {
            //此处返回方块战利品表
            // this.dropSelf(block.get());
        }
    }

        /* 模组自定义的方块战利品表必须覆盖此方法，以绕过对原版方块战利品表的检查（此处返回该模组的所有方块）
        @NotNull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Iterables.transform(BLOCKS.getEntries(), DeferredHolder::get);
        }

         */

    // 事件处理方法（NeoForge标准格式）
    private static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestItem.getItem() instanceof TimeWingsItem) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.2, 0));
            }
        }
    }

}