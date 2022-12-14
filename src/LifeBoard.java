public class LifeBoard {
    
    private int columns;
    private int rows;
    private Cell[][] cell;

    public LifeBoard(int w, int h) {
        
        columns = w;
        rows = h;

        cell = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j] = new Cell();
                cell[i][j].setCurrentState(false);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j].setNeighbours(initializeNeighbours(cell, i, j));
            }
        }

    }

    public LifeBoard(boolean[][] state) {

        rows = state.length;
        columns = state[0].length;

        cell = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j] = new Cell();
                cell[i][j].setCurrentState(state[i][j]);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j].setNeighbours(initializeNeighbours(cell, i, j));
            }
        }
    }


    // method to initialize the neighbours of each cell
    private Cell[] initializeNeighbours(Cell[][] cell, int i, int j) {

        Cell[] temp = null;

        if (i == 0) {
            if (j == 0) {
                // top left
                temp = new Cell[3];
                temp[0] = cell[i][j+1];
                temp[1] = cell[i+1][j];
                temp[2] = cell[i+1][j+1];

            }
            else if (j == columns-1) {
                // top right
                temp = new Cell[3];
                temp[0] = cell[i][j-1];
                temp[1] = cell[i+1][j-1];
                temp[2] = cell[i+1][j];
            }
            else {
                // top edge middle
                temp = new Cell[5];
                temp[0] = cell[i][j-1];
                temp[1] = cell[i][j+1];
                temp[2] = cell[i+1][j-1];
                temp[3] = cell[i+1][j];
                temp[4] = cell[i+1][j+1];
            }
        }
        else if (i == rows-1) {
            if (j == 0) {
                // bottom left
                temp = new Cell[3];
                temp[0] = cell[i-1][j];
                temp[1] = cell[i-1][j+1];
                temp[2] = cell[i][j+1];
            }
            else if (j == columns-1) {
                // bottom right
                temp = new Cell[3];
                temp[0] = cell[i-1][j-1];
                temp[1] = cell[i-1][j];
                temp[2] = cell[i][j-1];
            }
            else {
                // bottom edge middle
                temp = new Cell[5];
                temp[0] = cell[i-1][j-1];
                temp[1] = cell[i-1][j];
                temp[2] = cell[i-1][j+1];
                temp[3] = cell[i][j-1];
                temp[4] = cell[i][j+1];
            }
        }
        else if (j == 0) {
            // left edge middle
            temp = new Cell[5];
            temp[0] = cell[i-1][j];
            temp[1] = cell[i-1][j+1];
            temp[2] = cell[i][j+1];
            temp[3] = cell[i+1][j];
            temp[4] = cell[i+1][j+1];
        }
        else if (j == columns-1) {
            // right edge
            temp = new Cell[5];
            temp[0] = cell[i-1][j-1];
            temp[1] = cell[i-1][j];
            temp[2] = cell[i][j-1];
            temp[3] = cell[i+1][j-1];
            temp[4] = cell[i+1][j];
        }
        else {
            // middle
            temp = new Cell[8];
            temp[0] = cell[i-1][j-1];
            temp[1] = cell[i-1][j];
            temp[2] = cell[i-1][j+1];
            temp[3] = cell[i][j-1];
            temp[4] = cell[i][j+1];
            temp[5] = cell[i+1][j-1];
            temp[6] = cell[i+1][j];
            temp[7] = cell[i+1][j+1];
        } 

        return temp;   
    }

    // method to advance board to the next generation
    public void nextGeneration() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j].calculateFutureState();
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cell[i][j].setCurrentState(cell[i][j].getFutureState());;
            }
        }
    }

    // method to update the status (alive or dead) of every cell on the board
    public void setState(boolean[][] newState) {

        for (int i = 0; i < newState.length; i++) {
            for (int j = 0; j < newState[0].length; j++) {
                cell[i][j].setCurrentState(newState[i][j]);
            }
        }

    }

    // method to fill in the currentState parameter to match every cell on the board (alive or dead)
    public void getState(boolean[][] currentState)  {

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {

                currentState[i][j] = cell[i][j].getCurrentState();
            }
        }
    }

    // method to change the status of the cell at row r and column c on the board (from alive to dead, or dead to alive)
    public void toggleState(int r, int c) {

        if (cell[r][c].getCurrentState()) {
            cell[r][c].setCurrentState(false);
        }
        else {
            cell[r][c].setCurrentState(true);
        }
    }

}
