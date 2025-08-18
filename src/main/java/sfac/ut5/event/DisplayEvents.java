package sfac.ut5.event;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import sfac.ut5.Config;
import sfac.ut5.TimeSystem;
import sfac.ut5.UTVPlayerData;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.item.ITimeStorageItem;

/**
 * @author zer0M1nd
 */
@OnlyIn(Dist.CLIENT)
public class DisplayEvents {
    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(DisplayEvents::onLoggedIn);
        
        modEventBus.addListener(DisplayEvents::registerOverlays);
    }

    public static void onLoggedIn(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ITimeStorageItem ti) {
            if (ti.showTooltip(stack)) {
                event.getToolTip().add(1, Component.literal(ti.getFormattedTime(stack)));
            }
        }
    }

    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, TimeOverlay.ID, new TimeOverlay());
    }


    static abstract class Overlay implements LayeredDraw.Layer {
        public abstract void render(Minecraft mc, Player player, GuiGraphics guiGraphics, int guiTicks);

        @Override
        public final void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || !shouldRenderOverlay(mc, mc.player, guiGraphics, mc.gui.getGuiTicks()))
                return;

            render(mc, mc.player, guiGraphics, mc.gui.getGuiTicks());
        }

        public boolean shouldRenderOverlay(Minecraft mc, Player player, GuiGraphics guiGraphics, int guiTicks) {
            if (mc.options.hideGui || mc.gameMode == null) {
                return false;
            }
            if (mc.player == null) {
                return false;
            }
            return true;
        }
    }

    public static class TimeOverlay extends Overlay {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "time");

        @Override
        public void render(Minecraft mc, Player player, GuiGraphics guiGraphics, int guiTicks) {

//            UTVPlayerData data = player.getData(UTVPlayerData.UTVDATA);
            double scale = Config.TIME_DISPLAY_SCALE.getAsDouble() * 2;
            double x = Config.TIME_DISPLAY_X.getAsDouble();
            double y = Config.TIME_DISPLAY_Y.getAsDouble();
            String colorS = Config.TIME_DISPLAY_COLOR.get();
            int color = Integer.parseInt(colorS, 16);

            int h = guiGraphics.guiHeight();
            int w = guiGraphics.guiWidth();

            String textTime = TimeSystem.format(player.getData(UTVPlayerData.UTVDATA).getTime());

            // System.out.println("Tried!");

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale((float) scale, (float) scale, (float) scale);
            
            // Display only level > 0
            if (player.getData(UTVPlayerData.UTVDATA).getPlayerLevel() > 0)
	            guiGraphics.drawCenteredString(Minecraft.getInstance().font, textTime, (int) (x * w / scale), (int) (y * h / scale), color);
            
            guiGraphics.pose().popPose();
        }

        @Override
        public boolean shouldRenderOverlay(Minecraft mc, Player player, GuiGraphics guiGraphics, int guiTicks) {
            if (!super.shouldRenderOverlay(mc, player, guiGraphics, guiTicks)) {
                return false;
            }
            return true;
            //UTVPlayerData data = player.getData(UTVPlayerData.UTVDATA);
            //return data.isTimeRunning();
        }
    }
}