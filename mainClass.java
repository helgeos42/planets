public class mainClass {

    public static void main(String[] args) {
        StdDraw.setTitle("planets");
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
        //StdDraw.circle(0.5, 0.5, 0.01);

        double[] planetVelocityA = {0, 0.0021};
        double[] planetPositionA = {0.45, 0.5};

        double[] planetVelocityB = {0, -0.013};
        double[] planetPositionB = {0.7, 0.5};

        double[] planetVelocityC = {0, -0.001};
        double[] planetPositionC = {0.65, 0.5};

        planet myPlanets = new planet(planetPositionA, planetVelocityA, 5e5);

        myPlanets.addPlanet(planetPositionB, planetVelocityB, 1e5);
        myPlanets.addPlanet(planetPositionC, planetVelocityC, 3e4);

        while (true) {
            myPlanets.simulate();
        }
    }
}