import lejos.nxt.*;
import lejos.realtime.*;

public class RTLineLeader extends RealtimeThread{

	public RTLineLeader(int priority, long periodMillis)
	{
		super(
			new PriorityParameters(priority),
			new PeriodicParameters(new RelativeTime(periodMillis,0))
			 );
	}
	
	private Motor leftM = Motor.B;
	private Motor rightM = Motor.A;
	private int vB = 150;
	private NXTLineLeader captLine = new NXTLineLeader(SensorPort.S1);
	private boolean mode = true;
    	public void setMode(boolean pMode){
    		this.mode = pMode;
    	}

        public boolean getMode(){
        	return this.mode;
        }
        
        @Override
    	public void run() {
	        leftM.forward();
                rightM.forward();
        	while (this.mode){ //(true){
        	//if (this.mode){

            		int value = captLine.getResult();
            		switch ((value & 0x3C) >>> 2){
                	case 0x0C: // 1100
                	case 0x0E: // 1110
                		// tourner gauche
                		rightM.setSpeed(vB * 2);
                    		leftM.setSpeed(vB - vB/3);
                    		break;
			case 0x08: // 1000
				// tourner fortement gauche
				rightM.setSpeed(vB * 4);
                    		leftM.setSpeed(vB - (4*vB)/5); 
                    		break;
                    	//cas symetriques
                    	case 0x01: // 0001
                    	// tourner fortement droite
				leftM.setSpeed(vB * 4);
                    		rightM.setSpeed(vB - (4*vB)/5); 	 
                    		break;
                	case 0x03: // 0011
                	case 0x07: // 0111
                		//tourne a droite
                    		leftM.setSpeed(vB * 2);
                    		rightM.setSpeed(vB - vB/3);
                    		break;
                	case 0x00: // 0000
                	//stop !
                	leftM.setSpeed(vB * 2);
                        rightM.setSpeed(vB - vB/3);
                    	break;
                	default:
                		//va tout droit
                		leftM.setSpeed(vB);
                        	rightM.setSpeed(vB);
     
            		}
            		
            	waitForNextPeriod();
		}//end while
		
		rightM.stop();
		leftM.stop();
		System.out.println("stop");
		this.interrupt();
	}//end run()
}//end class()

