import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class Calibrate {

	public static void main(String[] args){

// Using the class difined in file NXTLineLeader.java from current directory (the version in LejosRT is out of date)		
		NXTLineLeader lineLeaderSensor = new NXTLineLeader(SensorPort.S1);
		
		LCD.clear();
		LCD.drawString("MindSensor calibration ", 0, 0) ;
		LCD.drawString("LineLeader " + lineLeaderSensor.getVersion(), 0, 1);
		
		lineLeaderSensor.wakeUp();
		
		LCD.drawString("White + Push button", 0, 5);
		Button.waitForPress();
		lineLeaderSensor.calibrate(NXTLineLeader.LineColor.WHITE) ;

		LCD.drawString("Black + Push button", 0, 5);
		Button.waitForPress();
		lineLeaderSensor.calibrate(NXTLineLeader.LineColor.BLACK) ;

		LCD.clear();
		LCD.drawString("OK! Button to quit", 0, 0);
		Button.waitForPress();

	}
	
}
