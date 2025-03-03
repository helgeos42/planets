public class planet {

    private double[] position = {0, 0};
    private double[] velocity = {0, 0};
    private double mass = 0;

    public int planetId = 0;
    public planet next = null;

    private forceStructure myGravity = null;


    public planet(double[] inputPosition, double[] inputVelocity, int inputMass){
        position = inputPosition;
        velocity = inputVelocity;
        mass = inputMass;

        myGravity = new forceStructure();
    }

    public planet(double[] inputPosition, double[] inputVelocity, int inputMass, forceStructure inputGravity){
        position = inputPosition;
        velocity = inputVelocity;
        mass = inputMass;

        myGravity = inputGravity;
    }


    public void reDraw(){

        StdDraw.circle(position[0], position[1], mass * 0.01);

        recalculate();

        if (next != null) {
            next.reDraw();
        }
    }
    
    private void recalculate(){

        calcForces();

        position[0] += velocity[0];
        position[1] += velocity[1];
    }

    private void calcForces(){

        forceStructure currentForce = myGravity;

        for (int i = 0; i < planetId; i++) {
            if (currentForce.nextRow == null) {
                currentForce.nextRow = new forceStructure();
            }
            currentForce = currentForce.nextRow;
        }
        planet calcToPlanet = next;
        while (calcToPlanet != null) {
            
        }
    }

    private double[] effectiveForce(forceStructure input){

    }

    public void addPlanet(double[] inputPosition, double[] inputVelocity, int inputMass){
        planet addAfter = lastPlanet();
        addAfter.next = new planet(inputPosition, inputVelocity, inputMass, myGravity);
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