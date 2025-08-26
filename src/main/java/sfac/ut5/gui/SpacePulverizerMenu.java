package sfac.ut5.gui;

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
import sfac.ut5.block.blockentity.SpacePulverizerBlockEntity;


public class SpacePulverizerMenu extends BaseContainerMenu{

    public final SpacePulverizerBlockEntity blockEntity;
    private final Level level;

    public SpacePulverizerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (SpacePulverizerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public SpacePulverizerMenu(int containerId, Inventory inv, SpacePulverizerBlockEntity blockEntity) {
        super(UTVGUITypes.TIME_SPINDLE_COUPLER_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = inv.player.level();

        addVanillaInventory(inv, 84);

        this.addSlot(new Slot(blockEntity, 0, 27, 47));
        this.addSlot(new Slot(blockEntity, 1, 76, 47));
        this.addSlot(new Slot(blockEntity, 2, 134, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
    }
    //返回容器槽位数量
    @Override
    protected int getContainerSlotCount() {
        return 3;
    }

    //判断gui对玩家是否有效
    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, UTVBlocks.TIME_SPINDLE_COUPLER.get());
    }

    public static class ContainerScreen<T extends AbstractContainerMenu> extends BaseContainerScreen<AbstractContainerMenu> {
        public ContainerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
            super(menu, playerInventory, title);
        }
    //返回gui的纹理位置
        @Override
        protected String getTextureLocation() {
            return "textures/gui/container/space_pulverizer.png";
        }
    }
}
