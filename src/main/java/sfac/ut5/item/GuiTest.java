package sfac.ut5.item;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuiTest extends Item {
    public GuiTest(Properties properties) {
        super(properties);
    }
    //Gui测试工具
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
          //  player.openMenu(new CustomAnvilMenuProvider());
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}