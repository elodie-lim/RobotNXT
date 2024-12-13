import lejos.nxt.Motor;

public class MoveXY {
    public static void main(String[] args) {
        // Les deux variables que le professeur peut changer
        double x = 10.0; // Distance en cm pour avancer
        double y = 5.0;  // Distance en cm pour reculer

        // Configuration : diamètre des roues
        double wheelDiameter = 4.0; // Diamètre de la roue en cm

        // Calcul de la circonférence de la roue
        double wheelCircumference = Math.PI * wheelDiameter;

        // Calcul des degrés nécessaires pour x et y
        int degreesForX = (int) ((x * 360) / wheelCircumference);
        int degreesForY = (int) ((y * 360) / wheelCircumference);

        // Fixer la vitesse des moteurs à 200 degrés par seconde
        Motor.A.setSpeed(200);
        Motor.B.setSpeed(200);

        // Avancer de x cm
        Motor.A.rotate(degreesForX, true); // Roue gauche
        Motor.B.rotate(degreesForX);      // Roue droite

        // Pause pour observer le mouvement
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Gérer l'interruption (si nécessaire)
        }

        // Reculer de y cm
        Motor.A.rotate(-degreesForY, true); // Roue gauche
        Motor.B.rotate(-degreesForY);      // Roue droite

        // Pause pour observer le mouvement
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Gérer l'interruption (si nécessaire)
        }
    }
}
