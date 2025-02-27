public class planet {

    private double[] position = {0, 0};
    private double[] velocity = {0, 0};
    private double mass = 0;

    public int planetId = 0;
    public planet next = null;

    public planet(double[] inputPosition, double[] inputVelocity, int inputMass){
        position = inputPosition;
        velocity = inputVelocity;
        mass = inputMass;
    }

    public static void main(String[] args) {
        StdDraw.setTitle("planets");
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
        //StdDraw.circle(0.5, 0.5, 0.01);

        double[] planetVelocityA = {0, 0.1};
        double[] planetPositionA = {0.5, 0.5};
        planet myPlanets = new planet(planetPositionA, planetVelocityA, 1);

        double[] planetVelocityB = {0, -0.1};
        double[] planetPositionB = {0.6, 0.5};
        myPlanets.addPlanet(planetPositionB, planetVelocityB, 1);

        while (true) {
            StdDraw.clear();
            myPlanets.reDraw();
            StdDraw.show();
            StdDraw.pause(1000);
        }
        
    }

    public void reDraw(){

        StdDraw.circle(position[0], position[1], mass * 0.01);

        recalculate();

        if (next != null) {
            next.reDraw();
        }
    }

    private void recalculate(){

    }

    public void addPlanet(double[] inputPosition, double[] inputVelocity, int inputMass){
        planet addAfter = lastPlanet();
        addAfter.next = new planet(inputPosition, inputVelocity, inputMass);
        lastPlanet().planetId = addAfter.planetId + 1;
    }

    private planet lastPlanet(){
        planet currentPlanet = this;
        while (currentPlanet.next != null) {
            currentPlanet = currentPlanet.next;
        }
        return currentPlanet;
    }


}