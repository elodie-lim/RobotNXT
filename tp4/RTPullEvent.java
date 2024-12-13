import lejos.realtime.*;
import lejos.nxt.*;

public class RTPullEvent extends RealtimeThread {

    private RTLineLeader rt_ll;

    public RTPullEvent(RTLineLeader prt_ll, int priority, long periodMillis) 
    {
        super(
        	new PriorityParameters(priority),
        	new PeriodicParameters(new RelativeTime(periodMillis,0))
           );
        this.rt_ll = prt_ll;
    }
    
    private static final Motor leftM = Motor.B;
    private static final Motor rightM = Motor.A;
    private static final TouchSensor btn = new TouchSensor(SensorPort.S2);

    @Override
    public void run() {
        while (true) {
            if (btn.isPressed() && rt_ll.getMode()) {
                //stop motors
                leftM.stop();
                rightM.stop();
                rt_ll.setMode(false);
            }
            else if (btn.isPressed() && !rt_ll.getMode()){
            	rt_ll.setMode(true);
            }
            waitForNextPeriod();
        }//end while
    }// end run()
}

