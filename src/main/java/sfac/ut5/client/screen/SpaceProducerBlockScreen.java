package sfac.ut5.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.gui.SpaceProducerBlockMenu;

public class SpaceProducerBlockScreen extends AbstractContainerScreen<SpaceProducerBlockMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "textures/gui/chronomantic_liber_prohibita.png");

    public SpaceProducerBlockScreen(SpaceProducerBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // 初始化 GUI 组件
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 绘制背景
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // 绘制能量进度条
        int energyHeight = (int) (50 * ((float) menu.getEnergyStored() / menu.getEnergyCapacity()));
        guiGraphics.blit(TEXTURE, leftPos + 10, topPos + 60 - energyHeight, 176, 0, 8, energyHeight);

        // 绘制流体进度条
        if (!menu.getFluidStored().isEmpty()) {
            int fluidHeight = (int) (50 * ((float) menu.getFluidStored().getAmount() / 10000));
            guiGraphics.blit(TEXTURE, leftPos + 154, topPos + 60 - fluidHeight, 184, 0, 8, fluidHeight);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}