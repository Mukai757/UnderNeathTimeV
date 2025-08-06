package underneathtimev.bus;

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
	public UTVEvents() {
		HelloWorldEvents.register();
		TimeWingsEvents.register();
		UpdatePlayerTimeEvents.register();
	}
}
