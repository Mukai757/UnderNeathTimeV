package underneathtimev.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(UT5Mod.MODID)
public class UT5Mod {
    public static final String MODID = "ut5";

    // 创建注册器
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 注册时之翼物品
    public static final DeferredItem<TimeWingsItem> TIME_WINGS = ITEMS.registerItem(
            "time_wings",
            TimeWingsItem::new,
            new Item.Properties().durability(432)
    );



    public UT5Mod(IEventBus modEventBus) {
        // 注册物品和标签页
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);

        // 注册事件监听器（NeoForge推荐方式）
        NeoForge.EVENT_BUS.addListener(UT5Mod::onLivingJump);
    }

    // 事件处理方法（NeoForge标准格式）
    private static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestItem.getItem() instanceof TimeWingsItem) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.2, 0));
            }
        }
    }


}