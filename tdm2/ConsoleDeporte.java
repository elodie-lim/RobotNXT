import lejos.nxt.comm.*;
import lejos.nxt.Battery;
import lejos.nxt.Button;

public class ConsoleDeporte {
	public static void main (String[] args) {
		while(!RConsole.isOpen()){
			RConsole.open();
		}
		RConsole.print("Battery level: "+Battery.getVoltage()+" V. \n");
		Button.waitForPress();
	}
}
