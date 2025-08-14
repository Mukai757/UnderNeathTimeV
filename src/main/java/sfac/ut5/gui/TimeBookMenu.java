package sfac.ut5.gui;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TimeBookMenu extends Screen {
    private final EditBox inputBox;

    protected TimeBookMenu(Component title) {
        super(title);
        this.inputBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Component.literal("输入文字"));
        this.inputBox.setResponder(this::onInputChanged);
    }

    private void onInputChanged(String newText) {
        // 处理输入变化的逻辑（例如打印到控制台）
        System.out.println("输入内容: " + newText);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // 绘制输入框
        this.inputBox.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // 将输入框的按键事件传递给父类处理（例如回车关闭GUI）
        if (this.inputBox.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        // 关闭GUI时的逻辑（例如保存数据）
        System.out.println("GUI已关闭");
        super.onClose();
    }
}