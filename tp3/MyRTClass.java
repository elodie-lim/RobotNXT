import lejos.nxt.*;
import lejos.realtime.*;
public class MyRTClass extends RealtimeThread{

    private RTLineLeader robotmourad;
    public MyRTClass(int priority, long periodMillis)
    {
   	    super(
            new PriorityParameters(priority),
            new PeriodicParameters(new RelativeTime(periodMillis,0))
             );
	    this.robotmourad = probotmourad;
    }
	
    private static final Motor leftM = Motor.B;
    private static final Motor rightM = Motor.A;
    private static final TouchSensor button = new TouchSensor(SensorPort.S2);     
//  add any sensor you want to use

    @Override
 public void run() {
        while (true) {
            if (button.isPressed() && robotmourad.getMode()) {
                // Stop motors
                leftM.stop();
                rightM.stop();
                robotmourad.setMode(false);
            } else if (button.isPressed() && !robotmourad.getMode()) {
                robotmourad.setMode(true);
            }

            waitForNextPeriod();
            // the code for the control goes here
            // TODO

        }// end while
    }// end run()
}// end class
// ()
