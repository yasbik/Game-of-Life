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
                temp[0].setCurrentState(cell[i][j+1].getCurrentState());
                temp[1].setCurrentState(cell[i+1][j].getCurrentState());
                temp[2].setCurrentState(cell[i+1][j+1].getCurrentState());

            }
            else if (j == columns-1) {
                // top right
                temp = new Cell[3];
                temp[0].setCurrentState(cell[i][j-1].getCurrentState());
                temp[1].setCurrentState(cell[i+1][j-1].getCurrentState());
                temp[2].setCurrentState(cell[i+1][j].getCurrentState());
            }
            else {
                // top edge middle
                temp = new Cell[5];
                temp[0].setCurrentState(cell[i][j-1].getCurrentState());
                temp[1].setCurrentState(cell[i][j+1].getCurrentState());
                temp[2].setCurrentState(cell[i+1][j-1].getCurrentState());
                temp[3].setCurrentState(cell[i+1][j].getCurrentState());
                temp[4].setCurrentState(cell[i+1][j+1].getCurrentState());

            }
        }
        else if (i == rows-1) {
            if (j == 0) {
                // bottom left
                temp = new Cell[3];
                temp[0].setCurrentState(cell[i-1][j].getCurrentState());
                temp[1].setCurrentState(cell[i-1][j+1].getCurrentState());
                temp[2].setCurrentState(cell[i][j+1].getCurrentState());
            }
            else if (j == columns-1) {
                // bottom right
                temp = new Cell[3];
                temp[0].setCurrentState(cell[i-1][j-1].getCurrentState());
                temp[1].setCurrentState(cell[i-1][j].getCurrentState());
                temp[2].setCurrentState(cell[i][j-1].getCurrentState());
            }
            else {
                // bottom edge middle
                temp = new Cell[5];
                temp[0].setCurrentState(cell[i-1][j-1].getCurrentState());
                temp[1].setCurrentState(cell[i-1][j].getCurrentState());
                temp[2].setCurrentState(cell[i-1][j+1].getCurrentState());
                temp[3].setCurrentState(cell[i][j-1].getCurrentState());
                temp[4].setCurrentState(cell[i][j+1].getCurrentState());
            }
        }
        else if (j == 0) {
            // left edge middle
            temp = new Cell[5];
            temp[0].setCurrentState(cell[i-1][j].getCurrentState());
            temp[1].setCurrentState(cell[i-1][j+1].getCurrentState());
            temp[2].setCurrentState(cell[i][j+1].getCurrentState());
            temp[3].setCurrentState(cell[i+1][j].getCurrentState());
            temp[4].setCurrentState(cell[i+1][j+1].getCurrentState());
        }
        else if (j == columns-1) {
            // right edge
            temp = new Cell[5];
            temp[0].setCurrentState(cell[i-1][j-1].getCurrentState());
            temp[1].setCurrentState(cell[i-1][j].getCurrentState());
            temp[2].setCurrentState(cell[i][j-1].getCurrentState());
            temp[3].setCurrentState(cell[i+1][j-1].getCurrentState());
            temp[4].setCurrentState(cell[i+1][j].getCurrentState());
        }
        else {
            // middle
            temp = new Cell[8];
            temp[0].setCurrentState(cell[i-1][j-1].getCurrentState());
            temp[1].setCurrentState(cell[i-1][j].getCurrentState());
            temp[2].setCurrentState(cell[i-1][j+1].getCurrentState());
            temp[3].setCurrentState(cell[i][j-1].getCurrentState());
            temp[4].setCurrentState(cell[i][j+1].getCurrentState());
            temp[5].setCurrentState(cell[i+1][j-1].getCurrentState());
            temp[6].setCurrentState(cell[i+1][j].getCurrentState());
            temp[7].setCurrentState(cell[i+1][j+1].getCurrentState());
        }   
        return temp;   
    }

    // method to advance board to the next generation
    public void nextGeneration() {

    }

    // method to update the status (alive or dead) of every cell on the board
    public void setState(boolean[][] newState) {

    }

    // method to fill in the currentState parameter to match every cell on the board (alive or dead)
    public void getState(boolean[][] currentState)  {

    }

    // method to change the status of the cell at row r and column c on the board (from alive to dead, or dead to alive)
    public void toggleState(int r, int c) {

    }



}
