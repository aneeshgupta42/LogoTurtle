package controller;

import frontEnd.Moveable;
import frontEnd.UserInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/*
@author Libba Lawrence, Aneesh Gupta

The control is the interface between the front end and the back end
 */
public class Control {

  private Moveable myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private final CommandSet commandSet;
  private final UserInterface view;
  private final Map<String,String> variablesUsed = new TreeMap<>();
  private final Map<String,String> functionsUsed = new TreeMap<>();
  private String finalReturnValue;

  /*
  Initializes controller
   */
  public Control(UserInterface UI) {
    view = UI;
    commandSet = new CommandSet(this);
  }

  /*
  Returns the variables created by running the command to the view
  @return variablesUsed
   */
  public Map<String,String> getVariables() {
    return variablesUsed;
  }

  /*
  Returns the user defined functions to the view
  @return functionsUsed
   */
  public Map<String,String> getUserCommands() {return functionsUsed;}

  /*
  Returns the final return value of the command inputs run
  @return finalReturnValue
   */
  public String getFinalReturnValue(){return finalReturnValue;}

  /*
  Sets the commandInput the user gives
  @param command
   */
  public void setCommand(String command) {
    commandSet.setCommandInput(command);
  }
/*
Sets the language the user chooses
@param lang
 */
  public void setLanguage(String lang) {
    commandSet.setLanguage(lang);
  }
  /*
  Returns the ID of the turtle
  @return view.getMover().getMoverID()
   */
  public double getRecentID(){
    return view.getMover().getMoverID();
  }

  /*
  Parses the command and updates the functions and variables accordingly
   */
  public void parseCommand(){
    commandSet.setFinalReturnValue(null);
    commandSet.parseCommand();
    if(commandSet.getVariables()!=null)variablesUsed.putAll(commandSet.getVariables());
    if(commandSet.getFunctions()!=null)functionsUsed.putAll(commandSet.getFunctions());
    finalReturnValue = commandSet.getFinalReturnValue();
  }

  public int getNumTurtles(){
    return view.getNumTurtles();
  }

  public void passTurtle(Moveable mover) {
    myMover = mover;
    turtleRow = myMover.getMoverRow();
    turtleCol = myMover.getMoverCol();
    turtleAngle = myMover.getMoverAngle();
  }

  public double getTurtleCol() {
    return turtleCol;
  }

  public double getTurtleRow() {
    return turtleRow;
  }

  public void setPenDown(boolean mode){
    myMover.setPen(mode);
  }

  public boolean isPenDown(){
    return myMover.isPenDown();
  }

  public double getTurtleAngle() {
    return turtleAngle;
  }

  public void setTurtleVisible(boolean mode) {
    myMover.moverVisible(mode);
  }

  public boolean findTurtleVisibility() {
    return myMover.isMoverVisible();
  }

  public void updateTurtle(double col, double row, double angle, int distance) {
    turtleRow = myMover.getMoverRow() + row;
    turtleCol = myMover.getMoverCol() + col;
    turtleAngle = myMover.getMoverAngle() + angle;
    myMover.updateDistanceSoFar(distance);
    myMover.move(col, row, angle);
  }

  public int getTurtleDistance() {
    return myMover.getDistanceSoFar();
  }

  public void turtleHome(boolean clearScreen) {
    if (clearScreen) {
      myMover.clearScreen();
    } else {
      myMover.resetMover();
    }
  }

  public void updateTurtleActive(ArrayList<Double> now_active){
    view.setActiveTurtles(now_active);
  }

  public double getTurtleRelativeXPos() {
    return turtleCol - myMover.getMoverCenterXPos();
  }

  public double getTurtleRelativeYPos() {
    return myMover.getMoverCenterYPos() - turtleRow;
  }

  public void setShape(int choice){
    myMover.setImageUsingIndex(choice);
  }

  public int getShape(){
    return myMover.getCurrentImageIndex();
  }

  public void setPenColor(int index) {
    myMover.setCurrentPenColorByIndex(index);
  }

  public int getPenColor() {
    return myMover.getCurrentPenColorIndex();
  }

  public void setPenWidth(double penWidth) {
    myMover.setThickness(penWidth);
  }

  public void switchPaletteColor(int index, int red, int green, int blue) {
    view.changeColorGrid(index, red, green, blue);
  }

  public void setBackgroundColor(int index) {
    view.getDisplayWindow().setCurrentBackgroundColorByIndex(index);
  }
}

