public class planet {

    private final double gravityConstant = 6.6743e-11;

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

    public double getMass(){
        return mass;
    }

    public double[] getPosition(){
        return position;
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

            currentForce.value = gravityBetweenPlanets(this, calcToPlanet);

            calcToPlanet = calcToPlanet.next;

            if (currentForce.nextCol == null) {
                currentForce.nextCol = new forceStructure();
            }

            currentForce = currentForce.nextCol;
        }
    }

    private double[] gravityBetweenPlanets(planet planetA, planet planetB){

        double[] connectionAB = connectingVector(planetA.getPosition(), planetB.getPosition());
        double distance = vectorLength(connectionAB);

        double massA = planetA.getMass();
        double massB = planetB.getMass();

        double gravityValue = gravityConstant * massA * massB / Math.pow(distance, 2);

        double scalar = gravityValue / distance;
        double[] gravityVector = {connectionAB[0] * scalar, connectionAB[1] * scalar};

        return gravityVector;
    }

    private double[] effectiveForce(){
        
        double[] totalForce = {0, 0};

        for(int currentRow = 0; currentRow < planetId; currentRow++){

            forceStructure currentForce = myGravity;
            for (int i = 0; i < currentRow; i++) {
                currentForce = currentForce.nextRow;
            }

            for(int currentCol = 1; currentCol < (planetId - currentRow); currentCol++){
                currentForce = currentForce.nextCol;
            }

            totalForce[0] += currentForce.value[0];
            totalForce[1] += currentForce.value[1];
        }

        forceStructure currentForce = myGravity;
        for(int currentRow = 0; currentRow < planetId; currentRow++){
            currentForce = currentForce.nextRow;
        }

        while(currentForce != null){
            totalForce[0] += currentForce.value[0];
            totalForce[1] += currentForce.value[1];

            currentForce = currentForce.nextCol;
        }

        return totalForce;
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

    private double[] connectingVector(double[] vectorA, double[] vectorB){
        double newXval = vectorB[0] - vectorA[0];
        double newYval = vectorB[1] - vectorA[1];
        double[] newVector = {newXval, newYval};
        return newVector;
    }

    private double vectorLength(double[] vector) {
        double length = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2));
        return length;
    }


}