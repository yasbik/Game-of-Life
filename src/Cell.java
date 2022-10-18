public class Cell {
    
    private boolean currentState;
    private boolean futureState;
    

    // Cell[] middleNeighbours = new Cell[8];
    // Cell[] edgeNeighbours = new Cell[5];
    // Cell[] cornerNeighbours = new Cell[3];

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

}
