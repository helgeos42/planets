public class mainClass {

    public static void main(String[] args) {
        StdDraw.setTitle("planets");
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
        //StdDraw.circle(0.5, 0.5, 0.01);

        double[] planetVelocityA = {0, 0.01};
        double[] planetPositionA = {0.5, 0.5};
        planet myPlanets = new planet(planetPositionA, planetVelocityA, 1);

        double[] planetVelocityB = {0, -0.01};
        double[] planetPositionB = {0.6, 0.5};
        myPlanets.addPlanet(planetPositionB, planetVelocityB, 1);

        while (true) {
            StdDraw.clear();
            myPlanets.reDraw();
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
}