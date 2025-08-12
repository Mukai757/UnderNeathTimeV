package underneathtimev.gui;

import java.util.function.Supplier;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import underneathtimev.UnderneathTimeV;

/**
 * @author Mukai
 */
public class UTVGUITypes {
	public static final Supplier<MenuType<TimeAnvilMenu>> TIME_ANVIL_MENU = registerMenuType("time_anvil_menu", TimeAnvilMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
    		String name, IContainerFactory<T> factory) {
        return UnderneathTimeV.MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}