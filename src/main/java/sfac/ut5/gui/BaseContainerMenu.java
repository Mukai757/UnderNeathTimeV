package sfac.ut5.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public abstract class BaseContainerMenu extends AbstractContainerMenu {
	public static final int HOTBAR_SLOT_COUNT = 9;
    public static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    public static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    public static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public static final int FIRST_VANILLA_SLOT_INDEX = 0;
    public static final int FIRST_CONTAINER_SLOT_INDEX = FIRST_VANILLA_SLOT_INDEX + VANILLA_SLOT_COUNT;
    
    public static class OutputSlot extends Slot {
		public OutputSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}
		
		@Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
	
    protected BaseContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}

    /**
     * <b>THIS MUST BE ADDED BEFORE CONTAINER'S SLOTS</b>
     */
    public void addVanillaInventory(Inventory playerInventory, int yOffset) {
    	addVanillaInventory(playerInventory, 8, yOffset);
    }

    /**
     * <b>THIS MUST BE ADDED BEFORE CONTAINER'S SLOTS</b>
     */
    public void addVanillaInventory(Inventory playerInventory, int xOffset, int yOffset) {
    	addPlayerHotbar(playerInventory, xOffset, yOffset);
    	addPlayerInventory(playerInventory, xOffset, yOffset);
    }

    /**
     * <b>THIS MUST BE ADDED BEFORE CONTAINER'S SLOTS</b>
     */
    public void addPlayerInventory(Inventory playerInventory, int xOffset, int yOffset) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
            	this.addSlot(new Slot(playerInventory, l + i * 9 + 9, xOffset + l * 18, yOffset + i * 18));
            }
        }
    }

    /**
     * <b>THIS MUST BE ADDED BEFORE CONTAINER'S SLOTS</b>
     */
    public void addPlayerHotbar(Inventory playerInventory, int xOffset, int yOffset) {
        for (int i = 0; i < 9; ++i) {
        	this.addSlot(new Slot(playerInventory, i, xOffset + i * 18, 58 + yOffset));
        }
    }

    protected abstract int getContainerSlotCount();
    
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < FIRST_VANILLA_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, FIRST_CONTAINER_SLOT_INDEX, FIRST_CONTAINER_SLOT_INDEX + getContainerSlotCount(), false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < FIRST_CONTAINER_SLOT_INDEX + getContainerSlotCount()) {
            // Move to hotbar firstly, then first valid inventory
            if (!moveItemStackTo(sourceStack, FIRST_VANILLA_SLOT_INDEX, FIRST_VANILLA_SLOT_INDEX + HOTBAR_SLOT_COUNT, false)) {
                if (!moveItemStackTo(sourceStack, FIRST_VANILLA_SLOT_INDEX, FIRST_VANILLA_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else {
        	UnderneathTimeV.LOGGER.error("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
    
    public abstract static class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<AbstractContainerMenu> {
		public BaseContainerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title);
		}

		@Override
		protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
			var BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, this.getTextureLocation());
			graphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		}
		
		protected abstract String getTextureLocation();
    	
		@Override
		public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
			super.render(graphics, mouseX, mouseY, partialTick);
			this.renderTooltip(graphics, mouseX, mouseY);
		}
		
		@Override
		protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		    graphics.drawString(this.font, this.title, (this.getXSize() - this.font.width(this.title)) / 2, 3, 0x404040, false);
		}
    }
}
