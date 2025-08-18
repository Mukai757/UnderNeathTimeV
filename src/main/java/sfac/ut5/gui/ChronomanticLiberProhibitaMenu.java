package sfac.ut5.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public class ChronomanticLiberProhibitaMenu extends AbstractContainerMenu {
	protected ChronomanticLiberProhibitaMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv);
	}
	
	public ChronomanticLiberProhibitaMenu(int containerId, Inventory playerInventory) {
		super(UTVGUITypes.CHRONOMANTIC_LIVER_PROHIBITA_MENU.get(), containerId);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
	
	public static class ChronomanticLiberProhibitaScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<AbstractContainerMenu> {
	    private static final ResourceLocation TEXTURE = 
	    		ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "textures/gui/chronomantic_liber_prohibita.png");

	    public ChronomanticLiberProhibitaScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
	    	super(menu, playerInventory, title);
	    }

	    @Override
	    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
	    	this.renderBackground(graphics, mouseX, mouseY, partialTick);
	    }

		@Override
		protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
			int xOffset = (this.width - 192) / 2;
			int yOffset = (this.height - 192) / 2;
			graphics.blit(TEXTURE, xOffset, yOffset, 0, 0, 192, 192);
			// TODO Component.translate()
			graphics.drawWordWrap(this.font, Component.literal("Hello, world! 你好世界！ Underneath Time V 时空深渊V"), xOffset + 35, yOffset + 15, 115, 0x404040);
		}
	}
}
