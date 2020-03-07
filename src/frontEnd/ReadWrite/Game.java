package frontEnd.ReadWrite;

/**
 * Simple immutable value object representing simulation data.
 * Holds the data for simulation to help create the Grid.
 * @author Robert C. Duvall
 * extended by Aneesh Gupta, Shruthi Kumar, Chris Warren
 */
public class Game {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "sim";
    private static final int DEFAULT_VALUE = 0;
    private static final int MAX_CHOICES = 6;
    private static final int MAX_SHAPES = 2;
    private static final String NEGATIVE_VALUES = "Some values are Negative and out of bounds!!";

    // specific data values for this instance
    private String simulationName, author, myLayout;
    private int myChoice,isLayout, myRows, myShape, myCols;
    private float myProb;
    private double myThreshold;

    /**
     * Create game data from given data.
     */
    public Game(String simName, String authName, int choice, int islayout, int rows, int cols, String layout, int shape) {
        simulationName = simName;
        author = authName;
        myChoice = choice;
        if(choice>MAX_CHOICES || choice <0){
            myChoice = DEFAULT_VALUE;
            simulationName = "GameOfLife";
        }
        if(shape>MAX_SHAPES) {
            shape = DEFAULT_VALUE;
        }
        if(rows<=0||cols<=0||shape<0||islayout<0){
            throw new XMLException(NEGATIVE_VALUES, Game.DATA_TYPE);
        }
        isLayout = islayout;
        myRows = rows;
        myShape = shape;
        myCols = cols;
        myLayout = layout;
    }

    /***
     * Overloaded version of constructor with param for fire probability
     * @param simName: Name of Simulation
     * @param authName: author of sim file
     * @param choice: choice of sim
     * @param islayout: random vs given layout
     * @param rows: rows in sim
     * @param cols: cols in sim
     * @param prob: probCatch for Fire
     */
    //Fire - has probability
    public Game(String simName, String authName, int choice, int islayout, int rows, int cols, float prob, String layout, int shape){
        this(simName, authName, choice, islayout,rows,cols, layout, shape);
        this.myProb = prob;
    }
    /***
     * Overloaded version of constructor with param for segregation threshold
     * @param simName: Name of Simulation
     * @param authName: author of sim file
     * @param choice: choice of sim
     * @param islayout: random vs given layout
     * @param rows: rows in sim
     * @param cols: cols in sim
     * @param thresh: "Happiness" threshold for segregation
     */
    //Segregation - has threshold
    public Game(String simName, String authName, int choice, int islayout, int rows, int cols, double thresh, String layout, int shape){
        this(simName, authName, choice, islayout,rows,cols, layout, shape);
        this.myThreshold = thresh;
    }

    /***
     * Overloaded version of constructor with param for rps threshold
     * @param simName: Name of Simulation
     * @param authName: author of sim file
     * @param choice: choice of sim
     * @param islayout: random vs given layout
     * @param rows: rows in sim
     * @param cols: cols in sim
     * @param thresh: threshold for rps rounds
     */
    //RPS - has threshold
    public Game(String simName, String authName, int choice, int islayout, int rows, int cols, int thresh, String layout, int shape){
        this(simName, authName, choice, islayout,rows,cols, layout, shape);
        this.myThreshold = thresh;
    }

    // NOTE: provides getters, but not setters
    /**
     * Returns title of this game.
     */
    public String getSimulationName() {
        return simulationName;
    }

    /***
     * returns initial layout of cell states in the grid
     * @return Layout: Returns the string layout
     */
    public String getMyLayout() {
        return myLayout;
    }

    /**
     * Returns publisher of this game.
     */
    public String getAuthor() {
        return author;
    }

    /***
     * returns the number of columns in the game grid
     * @return myCols
     */
    public int getMyCols() {
        return myCols;
    }

    /***
     * returns the number of rows in the game grid
     * @return myRows
     */
    public int getMyRows() {
        return myRows;
    }

    /***
     * returns the size of the grid (for future use)
     * @return mySize
     */
    public int getIsLayout() {
        return isLayout;
    }

    /***
     * returns the choice of simulation
     * @return my Choice
     */
    public int getMyChoice() {
        return myChoice;
    }

    /**
     * returns the prob of catching fire for the fire simulation
     */
    public float getMyProb() {
        return myProb;
    }

    /***
     * returns the Thrshold for simulation
     * @return myThreshold
     */
    public double getMyThreshold() {
        return myThreshold;
    }

    /***
     * get what shape (square, triangle, or hexagon)
     * is in plane rn, represented by code
     * @return myshape: code
     */
    public int getMyShape() {
        return myShape;
    }
}
