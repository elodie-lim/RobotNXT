import lejos.nxt.*;

public class DriveCircle {

    public static final Motor LM = Motor.A;
    public static final Motor RM = Motor.B;


    public static final double RADIUS       = 126.0/1000;   // 12,6cm
    public static final double SPEED        = 5.0/100;  // 5cm/sec
    public static final double W_GAP        = 14.0/100; // 14cm 
    public static final double W_RADIUS     = 2.0/100;  // 2cm 

    public static void main (String[] args) {

        System.out.println("Drive Circle");
        Button.waitForPress();

        double lws = ((RADIUS - W_GAP/2) * (SPEED/RADIUS))/W_RADIUS;
        double rws = ((RADIUS + W_GAP/2) * (SPEED/RADIUS))/W_RADIUS;

        int lwsd = (int)Math.toDegrees(lws); 
        int rwsd = (int)Math.toDegrees(rws); 

        LM.setSpeed(lwsd ); // degree/sec
        RM.setSpeed(rwsd );

        LM.forward();
        RM.forward();

        Button.waitForPress();

    }
}
