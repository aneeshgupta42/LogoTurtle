package controller;

import frontEnd.Moveable;
import frontEnd.UserInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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

  public Map<String,String> getVariables() {
    return variablesUsed;
  }

  public Map<String,String> getUserCommands() {return functionsUsed;}

  public String getFinalReturnValue(){return finalReturnValue;}

  public void setCommand(String command) {
    commandSet.setCommandInput(command);
  }

  public void setLanguage(String lang) {
    commandSet.setLanguage(lang);
  }

  public void parseCommand(){
    commandSet.setFinalReturnValue(null);
    commandSet.parseCommand();
    if(commandSet.getVariables()!=null)variablesUsed.putAll(commandSet.getVariables());
    if(commandSet.getFunctions()!=null)functionsUsed.putAll(commandSet.getFunctions());
    finalReturnValue = commandSet.getFinalReturnValue();
    System.out.println("FINAL RETURN VALUE ------>" + finalReturnValue);
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
    //myMover.setCurrentPenColorIndex(index);
  }

  public int getPenColor() {
    return myMover.getCurrentPenColorIndex();
  }

  public void setPenWidth(double penWidth) {
    myMover.setThickness(penWidth);
  }

  public void switchPaletteColor(int index, int red, int blue, int green) {
    // add call to cayla's method
  }

  public void setBackgroundColor(int index) {
    // add call to cayla's method
  }
}

