public class planet {

    private final double gravityConstant = 6.6743e-11;

    private final double planetRadiusMultiplier = 1e-7;
    private final int pauseMilliseconds = 20;
    private final double simulationTimeMultiplier = 0.1;

    private double[] position = {0, 0};
    private double[] velocity = {0, 0};
    private double mass = 0;

    public int planetId = 0;
    public planet next = null;

    private forceStructure myGravity = null;
    //Erlaeuterung zur Datenstruktur siehe Dokumentation


    public planet(double[] inputPosition, double[] inputVelocity, double inputMass){
        position = inputPosition;
        velocity = inputVelocity;
        mass = inputMass;

        myGravity = new forceStructure();
    }

    public planet(double[] inputPosition, double[] inputVelocity, double inputMass, forceStructure inputGravity){
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

    public void simulate(){
        StdDraw.clear();
        draw();
        StdDraw.show();
        StdDraw.pause(pauseMilliseconds);

        calcNewPosition();
    }

    public void draw(){

        StdDraw.circle(position[0], position[1], mass * planetRadiusMultiplier);

        if (next != null) {
            next.draw();
        }
    }
    
    private void calcNewPosition(){

        double[] myForce = calcForce();
        double[] acceleration = calcAcceleration(myForce);

        velocity[0] += acceleration[0] * simulationTimeMultiplier;
        velocity[1] += acceleration[1] * simulationTimeMultiplier;

        position[0] += velocity[0] * simulationTimeMultiplier;
        position[1] += velocity[1] * simulationTimeMultiplier;

        if (next != null) {
            next.calcNewPosition();
        }
    }

    private double[] calcForce(){

        double[] totalForce = addUpExistingForces();

        forceStructure currentForce = myGravity;

        for (int currentRow = 0; currentRow < planetId; currentRow++) {
            if (currentForce.nextRow == null) {
                currentForce.nextRow = new forceStructure();
            }
            currentForce = currentForce.nextRow;
        }

        planet calcToPlanet = next;
        while (calcToPlanet != null) {

            currentForce.value = gravityBetweenPlanets(this, calcToPlanet);
            totalForce[0] += currentForce.value[0];
            totalForce[1] += currentForce.value[1];

            calcToPlanet = calcToPlanet.next;

            if (currentForce.nextCol == null) {
                currentForce.nextCol = new forceStructure();
            }

            currentForce = currentForce.nextCol;
        }
        return totalForce;
    }

    private double[] gravityBetweenPlanets(planet planetA, planet planetB){

        double[] connectionAB = connectingVector(planetA.getPosition(), planetB.getPosition());
        double distance = vectorLength(connectionAB);

        double massA = planetA.getMass();
        double massB = planetB.getMass();

        if (distance == 0) {
            distance = 1;
        }

        double gravityValue = gravityConstant * massA * massB / Math.pow(distance, 2);

        double scalar = gravityValue / distance;
        double[] gravityVector = {connectionAB[0] * scalar, connectionAB[1] * scalar};

        return gravityVector;
    }

    private double[] addUpExistingForces(){

        double[] totalForce = {0, 0};

        for(int currentRow = 0; currentRow < planetId; currentRow++){

            forceStructure currentForce = myGravity;
            for (int i = 0; i < currentRow; i++) {
                currentForce = currentForce.nextRow;
            }

            int myForceCol = planetId - currentRow;
            for(int currentCol = 1; currentCol < myForceCol; currentCol++){
                currentForce = currentForce.nextCol;
            }

            totalForce[0] += (-1 * currentForce.value[0]);
            totalForce[1] += (-1 * currentForce.value[1]);
        }

        return totalForce;
    }

   
    private double[] calcAcceleration(double[] force){
        double accelerationX = force[0] / mass;
        double accelerationY = force[1] / mass;
        
        double[] acc = {accelerationX, accelerationY};
        return acc;
    }

    
    public void addPlanet(double[] inputPosition, double[] inputVelocity, double inputMass){
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