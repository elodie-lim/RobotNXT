import lejos.nxt.*;
import lejos.realtime.*;

public class RTOdometry extends RealtimeThread{
    private double x0;
    private double y0;
    private double x;
    private double y;
    private double teta;
    private double refLM;
    private double refRM;
    private Motor RM;
    private Motor LM;
    private double W_GAP;
    private double W_RADIUS;
    private RTLineLeader rt_ll;
    private RTShowCoords rt_show_coords;

    public RTOdometry(RTLineLeader rt_ll, int priority, long periodInMillis){
        super(
            new PriorityParameters(priority),
            new PeriodicParameters(new RelativeTime(periodInMillis,0))
        );

        this.x0 = 0;
        this.y0 = 0;
        this.x = 0;
        this.y = 0;
        this.teta = 0;
        this.RM = Motor.B;
        this.LM = Motor.A;
        this.refRM = RM.getTachoCount();
        this.refLM = LM.getTachoCount();
        this.W_GAP = 16.2/100;
        this.W_RADIUS = 2.0/100;

        this.rt_ll = rt_ll;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getAngle(){
        return Math.toDegrees(this.teta);
    }

    @Override public void run(){
        while(this.getAngle() < 360){
            double angleLM = Math.toRadians(LM.getTachoCount()-refLM);
            double angleRM = Math.toRadians(RM.getTachoCount()-refRM);

            double dl = angleLM * W_RADIUS;
            double dr = angleRM * W_RADIUS;
            double R = W_GAP/2 * (dr + dl)/(dr - dl);
            double deltaAngle = (dl - dr) / (2 * R);

            if(Double.isInfinite(R) || Double.isNaN(R)){
            	//deltaAngle = 0;
                x += dl*Math.cos(this.teta);
                y += dl*Math.sin(this.teta);
            } else {
                x = x0 + R * Math.cos(this.teta - Math.PI/2);
                y = y0 - R * Math.sin(this.teta - Math.PI/2);
                this.teta = this.teta - deltaAngle;
                
                x = x0 + R * Math.cos(this.teta - Math.PI/2);
                y = y0 - R * Math.sin(this.teta - Math.PI/2);
            }

            refLM = LM.getTachoCount();
            refRM = RM.getTachoCount();

            waitForNextPeriod();
        }

        rt_ll.setMode(false);
        this.interrupt();
    }
}

