public class mainClass {

    public static void main(String[] args) {
        StdDraw.setTitle("planets");
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
        //StdDraw.circle(0.5, 0.5, 0.01);

        double[] planetVelocityA = {0, 0};
        double[] planetPositionA = {0.5, 0.5};

        double[] planetVelocityB = {0, -0.01};
        double[] planetPositionB = {0.55, 0.5};

        planet myPlanets = new planet(planetPositionA, planetVelocityA, 10e4);

        
        myPlanets.addPlanet(planetPositionB, planetVelocityB, 10e3);

        while (true) {
            myPlanets.simulate();
        }
    }
}