package sfac.ut5.fluid;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;
import sfac.ut5.UnderneathTimeV;

import java.util.function.Supplier;

public class ModFluidType {

    //流体类型
    // 图片的位置，这里的source和flow，直接使用的原版的，所以没有第一个参数传入modid。

    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID,"block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL =  ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID,"block/water_flow");
    public static final ResourceLocation Time_Fluid_RL =  ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID,"block/water_still");

    // 怎么获得流体类型FluidType的注册器，有一点特殊，不是直接在Registires类下，而是在NeoForgeRegistries.Keys下
    // 这应该是因为原版并没有流体类型FluidType这样的概念。
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, UnderneathTimeV.MOD_ID);

    // 我们看到使用了register这个方法，这个方法是我们自己写的。
    // 对于register在下面介绍，我们来看参数
    // 第一参数name，没什么好说的，第二个参数是对流体的类型的属性进行一些设置  FluidType.Properties这个类就是对属性的一些设置。
    // create返回一个properties实例，lightlevel设置亮度等级2，density设置密度，viscosity设置粘度，sound设置流体声音。
    // 这些数值是直接复制的水的，对于其中的一些具体的效果，就自己调试看看效果把。也可以大家弹幕评论补充
    public static final Supplier<FluidType> MY_FLUID_TYPE = register("time_fluid",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK));

    // 这个是我们自己写的注册的方法
    // 并没有什么特殊的，直接返回了new baseFludType的supplier方法。
    private static Supplier<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new TimeFluid(WATER_STILL_RL, WATER_FLOWING_RL, Time_Fluid_RL,
                0xA1E038D0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }
    // 记得注册到总线
    public static void register(IEventBus eventBus) {

        FLUID_TYPES.register(eventBus);
    }

}