package sfac.ut5.event;

import net.neoforged.bus.api.IEventBus;

/**
 * Event Master Registry. </br>
 * All events intended to fulfill a single function should create an independent
 * class and complete registration in <code>register()</code>. These classes are
 * ultimately registered when the <code>register()</code> method of this class
 * is invoked.
 * 
 * @author AoXiang_Soar
 */

public class UTVEvents {

	private UTVEvents(){ // No one instantiate this!
	}

	public static void init(IEventBus modEventBus) {
		HelloWorldEvents.register();
		TimeWingsEvents.register();
		UpdatePlayerTimeEvents.register();
		BacktrackCompassEvents.register();
		DisplayEvents.register(modEventBus);
		NetworkEvents.register(modEventBus);
		TimeFluidEvents.register();
	}
}
