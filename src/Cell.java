public class Cell {
    
    private boolean currentState;
    private boolean futureState;

    Cell[] neighbours;


    /* Getters and setters */

    public Cell[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Cell[] neighbours) {
        this.neighbours = neighbours;
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

    public boolean getFutureState() {
        return futureState;
    }

    public void setFutureState(boolean futureState) {
        this.futureState = futureState;
    }



    public int calculateLivingNeighbours() {

        int livingNeighbours = 0;

        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i].currentState) {
                livingNeighbours++;
            }
        }

        return livingNeighbours;

    }

    public boolean calculateFutureState() {

        int livingNeighbours = calculateLivingNeighbours();

        if (currentState) {
            if (livingNeighbours < 2 || livingNeighbours > 3) {
                futureState = false;
            }
        }
        else {
            if (livingNeighbours == 3) {
                futureState = true;
            }
        }

        return futureState;
        
    }

}
