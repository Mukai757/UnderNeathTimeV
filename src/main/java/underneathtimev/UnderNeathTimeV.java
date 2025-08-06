package underneathtimev;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;

/**
 * @author AoXiang_Soar
 */

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import underneathtimev.bus.UTVEvents;
import underneathtimev.item.UTVItems;

@Mod(UnderNeathTimeV.MOD_ID)
public class UnderNeathTimeV {
	public static final String MOD_ID = "ut5";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
			"main_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ut5"))
					.withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> UTVItems.TIME_WINGS.get().getDefaultInstance())
					.displayItems((parameters, output) -> {
						output.accept(UTVItems.TIME_WINGS.get());
					}).build());

	public UnderNeathTimeV(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading UnderNeathTime V... This log was written on the first day of developing the mod."
				+ " Will there come a day when loading this mod requires traversing an abyss-like expanse of time?www");

		ITEMS.register(modEventBus);
		BLOCKS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		ATTACHMENT_TYPES.register(modEventBus);

		NeoForge.EVENT_BUS.register(this);

		new UTVItems();
		new UTVEvents();
		new TimeSystem();
		
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

}
