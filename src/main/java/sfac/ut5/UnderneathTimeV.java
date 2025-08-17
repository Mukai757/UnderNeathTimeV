package sfac.ut5;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.block.blockentity.UTVBlockEntities;
import sfac.ut5.component.UTVComponents;
import sfac.ut5.data.UTVDataGatherer;
import sfac.ut5.data.loot_table.UTVLootModifiers;
import sfac.ut5.event.UTVEvents;
import sfac.ut5.fluid.UTVFluids;
import sfac.ut5.gui.UTVGUITypes;
import sfac.ut5.fluid.UTVFluidTypes;
import sfac.ut5.item.UTVItems;

import java.util.LinkedList;
import java.util.List;

/**
 * @author AoXiang_Soar
 */
@Mod(UnderneathTimeV.MOD_ID)
public class UnderneathTimeV {
	public static final String MOD_ID = "ut5";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, UnderneathTimeV.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, UnderneathTimeV.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
	public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, UnderneathTimeV.MOD_ID);
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

	private static List<DeferredHolder<? extends ItemLike, ?>> items4MainTab = new LinkedList<>();
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
			"main_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ut5"))
					.withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> UTVItems.FATE_POCKET_WATCH.get().getDefaultInstance())
					.displayItems((parameters, output) -> {
						for(var item : items4MainTab) {
//							LOGGER.debug(item.get().toString());
							output.accept(item.get());
						}
					}).build());

	public UnderneathTimeV(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading Underneath Time V... This log was written on the first day of developing the mod."
				+ " Will there come a day when loading this mod requires traversing an abyss-like expanse of time?www");
		modEventBus.addListener(UTVDataGatherer::onGatherData);
		modEventBus.addListener(UTVFluids::onRegisterClientExtensions);
		modEventBus.addListener(UTVGUITypes::registerScreens);

		ITEMS.register(modEventBus);
		BLOCKS.register(modEventBus);
		BLOCK_ENTITY_TYPES.register(modEventBus);
		FLUIDS.register(modEventBus);
		FLUID_TYPES.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		ATTACHMENT_TYPES.register(modEventBus);
		DATA_COMPONENTS.register(modEventBus);
		MENUS.register(modEventBus);
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

		NeoForge.EVENT_BUS.register(this);

		UTVEvents.init(modEventBus);
		UTVItems.init();
		UTVBlocks.init();
		UTVBlockEntities.init();
		UTVFluids.init();
		UTVFluidTypes.init();
		TimeSystem.init();
		UTVComponents.init();
		UTVLootModifiers.init();
		UTVPlayerData.init();
		UTVGUITypes.init();

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.build());
	}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    @EventBusSubscriber(modid = UnderneathTimeV.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    public static <I extends ItemLike> void addItem2Tab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, DeferredHolder<? extends ItemLike, ?> item) {
    	if (tab == MAIN_TAB) {
    		items4MainTab.add(item);
    	}
    }

    /**
     * Use for datagen
     */
    public static List<DeferredHolder<Block, ? extends Block>> getKnownBlocks() {
        return UnderneathTimeV.BLOCKS.getEntries()
                .stream()
//                .map(e -> (Block) e.value())
                .toList();
    }

    /**
     * Use for datagen
     */
    public static List<DeferredHolder<Item, ? extends Item>> getKnownItems() {
        return UnderneathTimeV.ITEMS.getEntries()
                .stream()
//                .map(e -> (Item) e.value())
                .toList();
    }
}
