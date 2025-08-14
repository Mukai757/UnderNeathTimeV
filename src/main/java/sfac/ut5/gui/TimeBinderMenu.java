package sfac.ut5.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.block.blockentity.TimeBinderBlockEntity;

/**
 * @author Mukai
 */
public class TimeBinderMenu extends AbstractContainerMenu {
    public final TimeBinderBlockEntity blockEntity;
    private final Level level;

    public TimeBinderMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (TimeBinderBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public TimeBinderMenu(int containerId, Inventory inv, TimeBinderBlockEntity blockEntity) {
        super(UTVGUITypes.TIME_ANVIL_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = inv.player.level();

        for(int i = 0; i < blockEntity.getContainerSize(); i++) {
            this.addSlot(new Slot(blockEntity, i, 80+18*i, 35));
        }
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int FIRST_TINVENTORY_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, FIRST_TINVENTORY_SLOT_INDEX, FIRST_TINVENTORY_SLOT_INDEX
                    + INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < FIRST_TINVENTORY_SLOT_INDEX + INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
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
    
    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, UTVBlocks.TIME_SPINDLE_COUPLER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    
    public static class ContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<AbstractContainerMenu> {
		public ContainerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title);
		}

		@Override
		protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
			var BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "textures/gui/container/time_spindle_coupler.png");
			graphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		}
    	
		@Override
		public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
			super.render(graphics, mouseX, mouseY, partialTick);
			this.renderBackground(graphics, mouseX, mouseY, partialTick);
			this.renderTooltip(graphics, mouseX, mouseY);
		}
		
		@Override
		protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		    super.renderLabels(graphics, mouseX, mouseY);

		    // TODO 设置标题居中
		    graphics.drawString(this.font, this.title, 0, 0, 0x404040);
		}
    }
}