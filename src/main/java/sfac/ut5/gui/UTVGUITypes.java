package sfac.ut5.gui;

import java.util.function.Supplier;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;

/**
 * @author Mukai
 */
public class UTVGUITypes {
	public static final Supplier<MenuType<TimeSpindleCouplerMenu>> TIME_SPINDLE_COUPLER_MENU = registerMenuType("time_spindle_coupler", TimeSpindleCouplerMenu::new);
	public static final Supplier<MenuType<ChronomanticLiberProhibitaMenu>> CHRONOMANTIC_LIVER_PROHIBITA_MENU = registerMenuType("chronomantic_liber_prohibita", ChronomanticLiberProhibitaMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
    		String name, IContainerFactory<T> factory) {
        return UnderneathTimeV.MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
    
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(TIME_SPINDLE_COUPLER_MENU.get(), TimeSpindleCouplerMenu.ContainerScreen<AbstractContainerMenu>::new);
        event.register(CHRONOMANTIC_LIVER_PROHIBITA_MENU.get(), ChronomanticLiberProhibitaMenu.ChronomanticLiberProhibitaScreen<AbstractContainerMenu>::new);
    }
    
    public static void init() {}
}