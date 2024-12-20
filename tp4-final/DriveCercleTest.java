import lejos.nxt.*;

public class DriveCercleTest {

    public static final Motor LM = Motor.A; // Left Motor
    public static final Motor RM = Motor.B; // Right Motor

    // Constantes pour le robot
    public static final double WHEEL_RADIUS = 0.02; // Rayon des roues en mètres (2 cm)
    public static final double WHEEL_BASE = 0.1325; // Distance entre les roues en mètres (14 cm)
    public static final long UPDATE_PERIOD = 50; // Période de mise à jour en millisecondes
    public static final double STOP_THRESHOLD = 0.05; // Seuil pour considérer que le robot est revenu à sa position initiale (5 cm)

    public static void main(String[] args) {
        System.out.println("Drive Circle");
        Button.waitForPress();

        // Instanciation et démarrage du thread d'odométrie
        RTOdometry rtOdometry = new RTOdometry();
        rtOdometry.start();

        // Commande des moteurs pour avancer
        LM.setSpeed(0); // Vitesse du moteur gauche (en degrés par seconde)
        RM.setSpeed(250); // Vitesse du moteur droit (en degrés par seconde)

        LM.forward();
        RM.forward();

        // Attente de l'arrêt par le thread d'odométrie
        try {
            rtOdometry.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Arrêt des moteurs
        LM.stop();
        RM.stop();
        System.out.println("Robot stopped.");
    }

    static class RTOdometry extends Thread {
        private double x = 0.0; // Position X en mètres
        private double y = 0.0; // Position Y en mètres
        private double theta = 0.0; // Orientation en radians

        private int prevLeftTacho = 0; // Valeur précédente du tacho gauche
        private int prevRightTacho = 0; // Valeur précédente du tacho droit

        private volatile boolean running = true;
        private boolean hasMoved = false; // Indique si le robot a commencé à bouger

        @Override
        public void run() {
            while (running) {
                try {
                    // Lecture des tacho counts
                    int currentLeftTacho = LM.getTachoCount();
                    int currentRightTacho = RM.getTachoCount();

                    // Calcul des déplacements des roues
                    double deltaLeft = (currentLeftTacho - prevLeftTacho) * Math.PI / 180.0 * WHEEL_RADIUS;
                    double deltaRight = (currentRightTacho - prevRightTacho) * Math.PI / 180.0 * WHEEL_RADIUS;

                    // Mise à jour des tachomètres précédents
                    prevLeftTacho = currentLeftTacho;
                    prevRightTacho = currentRightTacho;

                    // Calcul des variations
                    double deltaTheta = (deltaRight - deltaLeft) / WHEEL_BASE;
                    double deltaDistance = (deltaRight + deltaLeft) / 2.0;

                    if (Double.isInfinite(deltaTheta) || Double.isNaN(deltaTheta)) {
                        deltaTheta = 0.0;
                    }

                    if (deltaTheta != 0) {
                        double radius = deltaDistance / deltaTheta;
                        double cx = x - radius * Math.sin(theta);
                        double cy = y + radius * Math.cos(theta);

                        theta += deltaTheta;
                        x = cx + radius * Math.sin(theta);
                        y = cy - radius * Math.cos(theta);
                    } else {
                        // Mouvement en ligne droite
                        x += deltaDistance * Math.cos(theta);
                        y += deltaDistance * Math.sin(theta);
                    }

                    // Indiquer que le robot a bougé
                    if (!hasMoved && (Math.abs(x) > STOP_THRESHOLD || Math.abs(y) > STOP_THRESHOLD)) {
                        hasMoved = true;
                    }

                    // Affichage toutes les secondes
                    if (System.currentTimeMillis() % 1000 < UPDATE_PERIOD) {
                        System.out.println("Position: x=" + x + ", y=" + y + ", theta=" + theta);
                    }

                    // Vérification de la condition d'arrêt (retour à (0, 0) après un déplacement significatif)
                    if (hasMoved && Math.abs(x) < STOP_THRESHOLD && Math.abs(y) < STOP_THRESHOLD) {
                        running = false;
                    }

                    // Attente avant la prochaine mise à jour
                    Thread.sleep(UPDATE_PERIOD);
                } catch (InterruptedException ex) {
                    System.out.println("Thread interrupted.");
                    running = false;
                }
            }
        }

        public void terminate() {
            running = false;
        }
    }
}

