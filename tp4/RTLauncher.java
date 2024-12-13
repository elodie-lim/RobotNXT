import lejos.nxt.*;
public class RTLauncher{

    public static void main(String[] args){
        RTLineLeader rt_ll = new RTLineLeader(15, 60);
        RTPullEvent rt_pe = new RTPullEvent(rt_ll, 13, 100);
        RTOdometry rt_odom = new RTOdometry(rt_ll, 14, 50);
        RTShowCoords rt_show_coords = new RTShowCoords(rt_odom, 12, 1000);

        rt_pe.start();
        rt_ll.start();
        rt_odom.start();
        rt_show_coords.start();

        try{
            rt_odom.join();
            rt_pe.join();
            rt_ll.join();
            rt_show_coords.join();
        }catch(InterruptedException e){
            System.out.println("ie in launcher");
        }
    }
}

