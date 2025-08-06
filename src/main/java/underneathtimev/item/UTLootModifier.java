package underneathtimev.item;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

    public class UTLootModifier extends LootModifier {
        // 编解码器见下文。
        public static final MapCodec<UTLootModifier> CODEC = null;
        // 我们的额外属性。
        private final String field1;
        private final int field2;
        private final Item field3;


        // 第一个构造函数参数是条件列表，其余是我们的额外属性。
        public UTLootModifier(LootItemCondition[] conditions, String field1, int field2, Item field3) {
            super(conditions);
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        // 返回我们的编解码器。
        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }

        // 这是实际应用修改器的地方。如果需要，可以使用额外属性。
        // 参数是现有的战利品和战利品上下文。
        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            // 在这里将你的物品添加到 generatedLoot。
            return generatedLoot;
        }
    }

