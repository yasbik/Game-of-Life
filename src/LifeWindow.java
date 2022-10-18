/* This gives a graphics window using "real" Java graphics, and
 * demonstrates how to determine the size of the drawing area,
 * do standard graphics, and use the mouse, keyboard, and a Timer.
 * 
 * This program provides a test bed for the Cell and LifeBoard
 * classes in Assignment 2 in COMP 1020 for the Fall 2017 term.
 * It will not function without those classes.
 * 
 * There is a set of final static constants a the top of the program
 * which can be adjusted to control the look and feel of the program.
 * 
 * J. Bate
 * October, 2017
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //This is needed for the mouse and key events

public class LifeWindow extends JFrame { //JFrame means "window", in effect
  
  //Constants to control everything
  final static int DEFAULT_WINDOW_WIDTH = 800;
  final static int DEFAULT_WINDOW_HEIGHT = 1000;
  final static int TEXT_AREA_SIZE = 200; //Pixels at the bottom to draw text in
  final static int MARGIN = 20; //Margin around the edges of the board in the top half
  final static Color TOP_COLOUR = new Color(192,192,192); //Light Grey
  final static Color BOARD_COLOUR = new Color(240,240,170); //Light yellow
  final static Color TEXT_AREA_COLOUR = new Color(255,255,255); //White
  final static int TEXT_LINE_SPACING = 32; //Distance from one line to the next, in pixels
  final static int TEXT_MARGIN = 16; //Left margin for the text at the bottom
  final static int INSET = 2; //Small inset (in pixels) all around the circle drawn in a cell
  final static int SPEED = 200; //Time between generations when on "play" (in msecs)
  final static int SMALL = 10; //The size of board generated if the user selects "small"
  final static int MEDIUM = 20; //and medium
  final static int LARGE = 40; //and large
  
  
  private LifePanel wholeWindow; //A JPanel is the content within a window
  private int width, height;  //For convenience. The drawing area, in pixels.
  private int squareSize; //The size of each square, in pixels
  private int lifeRows,lifeCols; //The size of the life board, in squares (cells)
  private LifeBoard theBoard; //The board itself. This class must be defined properly.
  
  private boolean running; //True if playing, false if paused.
  
  //These objects allow the simulation to run automatically at the indicated SPEED
  private ActionListener doOneGeneration;
  private Timer myTimer;
  
  //Constructor
  public LifeWindow(int rows, int cols) {
    setTitle("Game of Life");
    setSize(DEFAULT_WINDOW_WIDTH,DEFAULT_WINDOW_HEIGHT);
    lifeRows = rows; lifeCols = cols;
    theBoard = new LifeBoard(randomBooleanArray(rows,cols));
    
    //Everything is now set up by creating and using a whole lot of Objects
    wholeWindow = new LifePanel(this); //LifePanel is defined below
    add(wholeWindow);
    wholeWindow.addMouseListener(new HandleMouse());
    addKeyListener(new HandleKeys());
    setFocusable(true);
    requestFocusInWindow();
    setVisible(true);
    running=false;
    doOneGeneration = new ActionListener(){
      public void actionPerformed(ActionEvent event) {
        theBoard.nextGeneration();
        repaint();
      };
    };
    myTimer = new Timer(SPEED,doOneGeneration);
  }//SampleGraphicsWindow constructor
  
  public static void main(String[] args){
    //Simple main program for testing. Just create a window.
    LifeWindow test = new LifeWindow(10,15);
  }//main
  
  private class LifePanel extends JPanel {
    
    private LifeWindow myWindow;
    
    public LifePanel(LifeWindow window){
      myWindow = window;
    }
    
    public void paintComponent(Graphics g){
      /* This is where all the drawing commands will go.
       * Whenever the window needs to be drawn, or redrawn,
       * this method will automatically be called.
       */
      
      //Set all of the size variables to their current values,
      //which may change as a result of window resizing
      setSizes(g);
      
      int divider = height-TEXT_AREA_SIZE;
      
      g.setColor(TEXT_AREA_COLOUR);
      g.fillRect(0,0,width-1,height-1); //Draw the whole window the text area colour
      g.setColor(TOP_COLOUR);
      g.fillRect(0,0,width,divider); //Redraw the top half in its colour
      g.setColor(BOARD_COLOUR);
      g.fillRect(MARGIN,MARGIN,lifeCols*squareSize,lifeRows*squareSize); //Then the board
      
      //Draw a dividing line
      g.setColor(Color.BLACK);
      g.drawLine(0,divider,width-1,divider);
      
      //Draw the instructions at the bottom
      g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
      g.drawString("Click on a cell to toggle it, or press a key:", TEXT_MARGIN, divider+TEXT_LINE_SPACING);
      g.drawString("P:play/pause  G:next generation  R:randomize", TEXT_MARGIN, divider+2*TEXT_LINE_SPACING);
      g.drawString("Test boards - 1 to "+LifeTestCases.numTests(), TEXT_MARGIN, divider+3*TEXT_LINE_SPACING);
      g.drawString("Blank boards - S:small  M:medium  L:large", TEXT_MARGIN, divider+4*TEXT_LINE_SPACING);
      
      //Draw a grid for the board
      for(int r=0; r<=lifeRows; r++)
        g.drawLine(MARGIN,MARGIN+r*squareSize,MARGIN+lifeCols*squareSize,MARGIN+r*squareSize);
      for(int c=0; c<=lifeCols; c++)
        g.drawLine(MARGIN+c*squareSize,MARGIN,MARGIN+c*squareSize,MARGIN+lifeRows*squareSize);
    
      //Draw the board
      boolean[][] currentState = new boolean[lifeRows][lifeCols];
      theBoard.getState(currentState);
      for(int r=0; r<lifeRows; r++)
        for(int c=0; c<lifeCols; c++)
          if(currentState[r][c])
            g.fillOval(MARGIN+c*squareSize+INSET,MARGIN+r*squareSize+INSET,
                       squareSize-INSET*2,squareSize-INSET*2);
    }//paintComponent method
    
  }//private inner class graphicsPanel
  
  private void setSizes(Graphics g){
    //Determines the maximum size that the individual cells (squares) can be,
    //while still fitting everything into the current window.
    Rectangle r = g.getClipBounds();
    width = r.width;
    height = r.height;
    //Allow for the required margins, and the text at the bottom.
    int availableWidth = width-2*MARGIN;
    int availableHeight = height-TEXT_AREA_SIZE-2*MARGIN;
    //Find the biggest square size that will fit in the space available.
    squareSize = Math.min(availableWidth/lifeCols,availableHeight/lifeRows); 
  }
  
  private boolean[][] randomBooleanArray(int rows,int cols){
    boolean[][] boolArray = new boolean[rows][cols];
    for(int r=0; r<rows; r++)
      for(int c=0; c<cols; c++)
        boolArray[r][c]=Math.random()<0.5;
    return boolArray;
  }
  
  private int getCol(int xClick){
    //Convert the x coordinate of a mouse click into a column number
    if(xClick<MARGIN || xClick>= MARGIN+lifeCols*squareSize)
      return -1;
    else
      return (xClick-MARGIN)/squareSize;
  }
 
 private int getRow(int yClick){
    //Convert the y coordinate of a mouse click into a row number
    if(yClick<MARGIN || yClick>= MARGIN+lifeRows*squareSize)
      return -1;
    else
      return (yClick-MARGIN)/squareSize;
  }
 
  private class HandleMouse implements MouseListener {
    //The five standard methods are required. I don't want these ones:
    public void mousePressed(MouseEvent e){ /*Do nothing */ }
    public void mouseReleased(MouseEvent e){ /*Do nothing */ }
    public void mouseEntered(MouseEvent e){ /*Do nothing */ }
    public void mouseExited(MouseEvent e){ /*Do nothing */ }
    
    //The only one we really want to pay attention to
    public void mouseClicked(MouseEvent e){
      int r=getRow(e.getY());
      int c=getCol(e.getX());
      if(r>=0 && c>=0)
        theBoard.toggleState(r,c);
      repaint(); //Redraw everything, since a change was made.
    }//mouseClicked
    
  }//private inner class HandleMouse
 
  private class HandleKeys implements KeyListener {
    //The standard methods are required.
    public void keyPressed(KeyEvent e){ /*Do nothing */ }
    public void keyReleased(KeyEvent e){ /*Do nothing */ }
    
    //The only one we really want to pay attention to
    public void keyTyped(KeyEvent e){
      char typed = Character.toLowerCase(e.getKeyChar());
      if(typed=='g'){
        theBoard.nextGeneration();
        repaint();
      }
      else if(typed=='p'){
        if(running = !running)
          myTimer.start();
        else
          myTimer.stop();
      }
      else if(typed=='r'){
        theBoard.setState(randomBooleanArray(lifeRows,lifeCols));
        repaint();
      }
      else if(Character.isDigit(typed)){
        int selected = Character.digit(typed,10);
        if(selected>0 && selected<=LifeTestCases.numTests())
          setNewBoard(LifeTestCases.getTest(selected-1));
      }

      else if(typed=='s'){
        setNewBoard(SMALL);
      }
      else if(typed=='m'){
        setNewBoard(MEDIUM);
      }
      else if(typed=='l'){
        setNewBoard(LARGE);
      }

    }//keyTyped

    private void setNewBoard(int size){
      theBoard = new LifeBoard(size,size);
      lifeRows = size;
      lifeCols = size;
      repaint();
    }

    private void setNewBoard(boolean[][] newState){
      theBoard = new LifeBoard(newState);
      lifeRows = newState.length;
      lifeCols = newState[0].length;
      repaint();
    }
    
  }//private inner class HandleKeys
  
}//class LifeWindow



