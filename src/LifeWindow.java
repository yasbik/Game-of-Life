import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LifeWindow extends JFrame {

    final static int DEFAULT_WINDOW_WIDTH = 800;
    final static int DEFAULT_WINDOW_HEIGHT = 1000;
    // bottom area to draw text
    final static int TEXT_AREA_SIZE = 200;
    // top half margin
    final static int MARGIN = 20;
    // left margin
    final static int TEXT_MARGIN = 16;

    // board colours
    final static Color TOP_COLOUR = new Color(100,180,100);
    final static Color BOARD_COLOUR = new Color(180, 255, 180);
    final static Color TEXT_AREA_COLOUR = new Color(220, 255, 220);

    final static int TEXT_LINE_SPACING = 32;
    final static int INSET = 2;

    // time delay
    final static int SPEED = 200;

    // small board
    final static int SMALL = 10;
    // medium board
    final static int MEDIUM = 20;
    // large board
    final static int LARGE = 40;

    private LifePanel wholeWindow; 
    private int width, height;
    // size of each square
    private int squareSize;
    // board size
    private int lifeRows, lifeCols;
    private LifeBoard theBoard;

    // play or pause
    private boolean running;

    // simulation processors
    private ActionListener doOneGeneration;
    private Timer myTimer;

    // Constructor
    public LifeWindow(int rows, int cols) {

        setTitle("Game of Life");
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        lifeRows = rows;
        lifeCols = cols;
        theBoard = new LifeBoard(randomBooleanArray(rows, cols));

        // setting up all objects
        wholeWindow = new LifePanel(this);
        add(wholeWindow);
        wholeWindow.addMouseListener(new HandleMouse());
        addKeyListener(new HandleKeys());
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
        running = false;
        
        doOneGeneration = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                theBoard.nextGeneration();
                repaint();
            };
        };

        myTimer = new Timer(SPEED, doOneGeneration);
    }

    // main function
    public static void main(String[] args) {
        
        // new window
        LifeWindow test = new LifeWindow(10, 15);
    }

    // method for the graphics panel
    private class LifePanel extends JPanel {

        private LifeWindow myWindow;

        public LifePanel(LifeWindow window) {
            myWindow = window;
        }

        // metho to draw the game window
        public void paintComponent(Graphics g) {

            // set window dimentions
            setSizes(g);

            int divider = height - TEXT_AREA_SIZE;

            // draw the window
            g.setColor(TEXT_AREA_COLOUR);
            g.fillRect(0, 0, width - 1, height - 1); 

            // draw the background
            g.setColor(TOP_COLOUR);
            g.fillRect(0, 0, width, divider); 

            // draw the board
            g.setColor(BOARD_COLOUR);
            g.fillRect(MARGIN, MARGIN, lifeCols * squareSize, lifeRows * squareSize); 

            // separate the text area
            g.setColor(Color.BLACK);
            g.drawLine(0, divider, width - 1, divider);

            // display the instructions
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
            g.drawString("Click on a cell to toggle it, or press a key:", TEXT_MARGIN, divider + TEXT_LINE_SPACING);
            g.drawString("P: Play/Pause  G: Next Generation  R: Randomize", TEXT_MARGIN, divider + 2 * TEXT_LINE_SPACING);
            g.drawString("Test boards - 1 to " + LifeTestCases.numTests(), TEXT_MARGIN, divider + 3 * TEXT_LINE_SPACING);
            g.drawString("Blank Boards - S: Small  M: Medium  L: Large", TEXT_MARGIN, divider + 4 * TEXT_LINE_SPACING);

            // loop to draw the grid
            for (int r = 0; r <= lifeRows; r++) {
                g.drawLine(MARGIN, MARGIN + r * squareSize, MARGIN + lifeCols * squareSize, MARGIN + r * squareSize);
            }
                
            for (int c = 0; c <= lifeCols; c++) {
                g.drawLine(MARGIN + c * squareSize, MARGIN, MARGIN + c * squareSize, MARGIN + lifeRows * squareSize);
            }

            // create the board
            boolean[][] currentState = new boolean[lifeRows][lifeCols];
            theBoard.getState(currentState);

            // draw the board
            for (int r = 0; r < lifeRows; r++) {
                for (int c = 0; c < lifeCols; c++) {
                    if (currentState[r][c]) {
                        g.fillOval(MARGIN + c * squareSize + INSET, MARGIN + r * squareSize + INSET, squareSize - INSET * 2, squareSize - INSET * 2);
                    }
                }
            }
                
        }
    }

    // method to set the object sizes
    private void setSizes(Graphics g) {

        // fix the cell size and window size so that everything fits
        Rectangle r = g.getClipBounds();
        width = r.width;
        height = r.height;

        // set the margins for the window and text area
        int availableWidth = width - 2 * MARGIN;
        int availableHeight = height - TEXT_AREA_SIZE - 2 * MARGIN;

        // ensure the biggest square fits the space
        squareSize = Math.min(availableWidth / lifeCols, availableHeight / lifeRows);
    }

    // method to create a random 2D array
    private boolean[][] randomBooleanArray(int rows, int cols) {

        // initialize
        boolean[][] boolArray = new boolean[rows][cols];
        
        // create the array of random values
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                boolArray[r][c] = Math.random() < 0.5;
            }
        }
        return boolArray;
    }

    // method to get the column number from the x coordinate of the cursor
    private int getCol(int xClick) {
        if (xClick < MARGIN || xClick >= MARGIN + lifeCols * squareSize) {
            return -1;
        }
        else {
            return (xClick - MARGIN) / squareSize;
        }
    }

    // method to get the row number from the y coordinate of the cursor
    private int getRow(int yClick) {
        if (yClick < MARGIN || yClick >= MARGIN + lifeRows * squareSize) {
            return -1;
        }
        else {
            return (yClick - MARGIN) / squareSize;
        }
    }

    // listener method for handling mouse clicks
    private class HandleMouse implements MouseListener {
        
        // event not needed
        public void mousePressed(MouseEvent e) {
            ;
        }

        // event not needed
        public void mouseReleased(MouseEvent e) {
            ;
        }

        // event not needed
        public void mouseEntered(MouseEvent e) {
            ;
        }

        // event not needed
        public void mouseExited(MouseEvent e) {
            ;
        }

        // the main event needed
        public void mouseClicked(MouseEvent e) {
            // get row and column
            int r = getRow(e.getY());
            int c = getCol(e.getX());

            // change the state of the board
            if (r >= 0 && c >= 0) {
                theBoard.toggleState(r, c);
            }

            // recreate the board with the changes
            repaint();
        }
    }

    // listener method to hangle key presses
    private class HandleKeys implements KeyListener {
        
        // event not needed
        public void keyPressed(KeyEvent e) {
            ;
        }

        // event not needed
        public void keyReleased(KeyEvent e) {
            ;
        }

        // the main event needed
        public void keyTyped(KeyEvent e) {
            char typed = Character.toLowerCase(e.getKeyChar());

            if (typed == 'g') {
                theBoard.nextGeneration();
                repaint();
            } 
            // play/pause
            else if (typed == 'p') {
                if (running = !running) {
                    myTimer.start();
                }
                else {
                    myTimer.stop();
                } 
            }
            // randomize
            else if (typed == 'r') {
                theBoard.setState(randomBooleanArray(lifeRows, lifeCols));
                repaint();
            } 
            else if (Character.isDigit(typed)) {
                int selected = Character.digit(typed, 10)
                ;
                if (selected > 0 && selected <= LifeTestCases.numTests()) {
                    setNewBoard(LifeTestCases.getTest(selected - 1));
                }
            }
            // small board
            else if (typed == 's') {
                setNewBoard(SMALL);
            } 
            // medium board
            else if (typed == 'm') {
                setNewBoard(MEDIUM);
            } 
            // large board
            else if (typed == 'l') {
                setNewBoard(LARGE);
            }
        }

        // method to set the sizes of a board according to a size
        private void setNewBoard(int size) {
            theBoard = new LifeBoard(size, size);
            lifeRows = size;
            lifeCols = size;
            repaint();
        }

        // method to set the sizes of a board according to a 2D array
        private void setNewBoard(boolean[][] newState) {
            theBoard = new LifeBoard(newState);
            lifeRows = newState.length;
            lifeCols = newState[0].length;
            repaint();
        }

    }

}
