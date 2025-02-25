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

        double[] planetVelocity = {0, 0.1};
        double[] planetPosition = {0.5, 0.5};
        planet myPlanets = new planet(planetVelocity, planetPosition, 1);

        planetVelocity[1] = -0.1;
        myPlanets.addPlanet(planetPosition, planetVelocity, 1);

        myPlanets.draw();
    }

    public void draw(){

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