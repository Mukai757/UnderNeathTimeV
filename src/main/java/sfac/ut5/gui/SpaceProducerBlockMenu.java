package sfac.ut5.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.blockentity.SpaceProducerBlockEntity;

public class SpaceProducerBlockMenu extends AbstractContainerMenu {
    private final SpaceProducerBlockEntity blockEntity;

    public SpaceProducerBlockMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, (SpaceProducerBlockEntity) playerInventory.player.level().getBlockEntity(data.readBlockPos()));
    }

    public SpaceProducerBlockMenu(int containerId, Inventory playerInventory, SpaceProducerBlockEntity blockEntity) {
        super(UTVGUITypes.SPACE_PRODUCER.get(), containerId);
        this.blockEntity = blockEntity;

        // 添加燃料槽（仅限燃料）
        addSlot(new Slot(blockEntity.getFuelHandler(), 0, 56, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });

        // 添加玩家物品栏
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 添加玩家快捷栏
        for (int slot = 0; slot < 9; ++slot) {
            addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();

            if (index == 0) {
                if (!moveItemStackTo(stackInSlot, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stackInSlot, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public int getEnergyStored() {
        return blockEntity.getEnergyStored();
    }

    public int getEnergyCapacity() {
        return blockEntity.getEnergyCapacity();
    }

    public FluidStack getFluidStored() {
        return blockEntity.getFluidStored();
    }
    //应该是渲染吧?
    public static class SpaceProducerBlockScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<AbstractContainerMenu> {
        private static final ResourceLocation TEXTURE =
                ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "textures/gui/space_producer_block_menu.png");

        public SpaceProducerBlockScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
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
