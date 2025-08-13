package sfac.ut5.fluid;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.item.UTVItems;

import java.util.function.Supplier;

public class UTVFluid {
    // 流体注册器
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, UnderneathTimeV.MOD_ID);

    // 注册对应流体的source和flow，使用NeoForge提供的BaseFlowingFluid来注册
    // 其中source和flow都需要填入一个参数，这个参数是流体的属性,在下面定义
    public static Supplier<FlowingFluid> MY_SOURCE_FLUID_BLOCK = FLUIDS.register("my_fluid", () -> new BaseFlowingFluid.Source(UTVFluid.MY_FLUID_PROPERTIES));
    public static Supplier<FlowingFluid> MY_FLOWING_FLUID_BLOCK = FLUIDS.register("my_fluid_flow", () -> new BaseFlowingFluid.Flowing(UTVFluid.MY_FLUID_PROPERTIES));
    // 定义流体的属性
    // 这个流体的属性要传入的内容比较多，我们挨个介绍，我们使用了BaseFlowingFluid的Properties内部类创建对应的Properties，其中第一个参数是对应的流体的类体类型FluidType，然后第二个参数是对应的source流体，第三个参数是flow流体，都是我们刚刚写过的，看起来比较绕，大家自己理清下关系。
    // 通过bucket这个设置流体和对应的流体桶的绑定，等会我们注册这个bucketitem
    // 通过block绑定对应的流体和方块的绑定，这个方块等会我们注册。
    // slopeFindDistance寻找斜坡的距离
    // levelDecreasePerBlock 每个方块流体的减少量。
    // 后两个数据是用于流体的流动的，主要是斜坡时候优先流，不会扩散。
    // 以及流体最多能流多远，例如原版的水是8格
    // 可以自己调试这几个数值试试，也可以去wiki看看具体的含义。
    private static final BaseFlowingFluid.Properties MY_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(ModFluidType.MY_FLUID_TYPE, UTVFluid.MY_SOURCE_FLUID_BLOCK, UTVFluid.MY_FLOWING_FLUID_BLOCK).bucket(UTVItems.TimeFileBUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(UTVBlocks.TIME_Fluid_Block);
    // 别忘记注册到总线
    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}