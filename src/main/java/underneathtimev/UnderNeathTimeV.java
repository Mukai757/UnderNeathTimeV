package underneathtimev;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.Minecraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import underneathtimev.component.UTVComponents;
import underneathtimev.event.UTVEvents;
import underneathtimev.item.UTVItems;
import underneathtimev.provider.UTVProviders;
import underneathtimev.provider.loot_table.UTVLootModifier;

/**
 * @author AoXiang_Soar
 */

@Mod(UnderNeathTimeV.MOD_ID)
public class UnderNeathTimeV {
	public static final String MOD_ID = "ut5";
	public static final String MODID = "ut5";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
	public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MOD_ID);
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);


	private static List<DeferredItem<? extends ItemLike>> items4MainTab = new LinkedList<>();
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
			"main_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ut5"))
					.withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> UTVItems.FATE_POCKET_WATCH.get().getDefaultInstance())
					.displayItems((parameters, output) -> {
						for(var item : items4MainTab)
							output.accept(item.get());
					}).build());

	public UnderNeathTimeV(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading UnderNeathTime V... This log was written on the first day of developing the mod."
				+ " Will there come a day when loading this mod requires traversing an abyss-like expanse of time?www");
		modEventBus.addListener(UTVProviders::onGatherData);

		ITEMS.register(modEventBus);
		BLOCKS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		ATTACHMENT_TYPES.register(modEventBus);
		DATA_COMPONENTS.register(modEventBus);
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

		NeoForge.EVENT_BUS.register(this);
		//NeoForge.EVENT_BUS.register(UTVProviders.class);

		new UTVItems();
		new UTVEvents();
		new TimeSystem();
		new UTVComponents();
		new UTVLootModifier();
		//new UTVProviders();
		
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    @EventBusSubscriber(modid = UnderNeathTimeV.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    
    public static <I extends Item> void addItem2Tab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, DeferredItem<I> item) {
    	if (tab == MAIN_TAB) {
    		items4MainTab.add(item);
    	}
    }
}
