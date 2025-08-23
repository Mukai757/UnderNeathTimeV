package sfac.ut5.gui;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.block.blockentity.SpaceProducerBlockEntity;
import sfac.ut5.item.UTVItems;

/**
 * @author AoXiang_Soar
 */
//But an exist abstract class is really GOOOOOOOOOOD! :)
public class SpaceProducerMenu extends BaseContainerMenu {
    public final SpaceProducerBlockEntity blockEntity;
    private final Level level;

    public SpaceProducerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (SpaceProducerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public SpaceProducerMenu(int containerId, Inventory inv, SpaceProducerBlockEntity blockEntity) {
        super(UTVGUITypes.SPACE_PRODUCER.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = inv.player.level();
        
        addVanillaInventory(inv, 84);
        
        this.addSlot(new Slot(blockEntity, 0, 80, 47) {
        	@Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(UTVItems.CHRONOSTICE_PETALS); // TODO 改燃料
            }
        });
    }

	@Override
	protected int getContainerSlotCount() {
		return 1;
	}
	
    @Override
    public boolean stillValid(Player player) {
    	var blocks = List.of(
    			UTVBlocks.SPACE_PRODUCER_0.get(),
    			UTVBlocks.SPACE_PRODUCER_1.get(),
    			UTVBlocks.SPACE_PRODUCER_2.get(),
    			UTVBlocks.SPACE_PRODUCER_3.get(),
    			UTVBlocks.SPACE_PRODUCER_4.get(),
    			UTVBlocks.SPACE_PRODUCER_5.get());
    	boolean flag = false;
    	for (var b : blocks) {
    		if (super.stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, b))
    			flag = true;
    	}
        return flag;
    }
    
    public static class ContainerScreen<T extends AbstractContainerMenu> extends BaseContainerScreen<AbstractContainerMenu> {
		public ContainerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title);
		}

		@Override
		protected String getTextureLocation() {
			return "textures/gui/container/space_producer.png";
		}
		
	    @Override
	    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
	        super.renderLabels(graphics, mouseX, mouseY);
	        
	        if (this.menu instanceof SpaceProducerMenu spaceMenu) {
	            var be = spaceMenu.blockEntity;
	            if (be != null) {
	                var context = be.getSpace() + "/" + be.getProducerLevel().getCapacity() + " m³";
	                
	                int textWidth = this.font.width(context);
	                int centerX = (this.imageWidth - textWidth) / 2;
	                
	                graphics.drawString(this.font, context, centerX, 15, 0x404040, false);
	            }
	        }
	    }
    }
}
