import lejos.nxt.*;
import lejos.realtime.*;
public class RTShowCoords extends RealtimeThread{

    private RTOdometry rt_odom;
    private double x;
    private double y;
    private double angle;
    public RTShowCoords(RTOdometry rt_odom, int priority, long periodInMillis)
    {
        super(
            new PriorityParameters(priority),
            new PeriodicParameters(new RelativeTime(periodInMillis,0))
        );
        this.rt_odom = rt_odom;
    }

    @Override
    public void run(){
        while(true){
            this.x = rt_odom.getX();
            this.y = rt_odom.getY();
            this.angle = rt_odom.getAngle();
            System.out.println("X : " + (int)(this.x));
            System.out.println("Y : " + (int)(this.y));
            System.out.println("angle : " + (int)(this.angle));
            waitForNextPeriod();
        }
    }
}
