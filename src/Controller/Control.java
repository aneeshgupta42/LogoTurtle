package Controller;

import frontEnd.Moveable;
import frontEnd.UserInterface;
import frontEnd.Viewable;

import java.util.Map;
import java.util.TreeMap;

public class Control {


  private Moveable myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private CommandGrouping commandGrouping;
  private String language;
  private String input;
  private Viewable view;
  private Map<String,String> variablesUsed = new TreeMap<>();
  private Map<String,String> functionsUsed = new TreeMap<>();
  private StoreLists lists;



  /*
  Initializing a control (for reference storeLists is where all the data in lists is being passed)
   */
  public Control(Viewable UI) {
    view = UI;
    commandGrouping = new CommandGrouping(this);
  }

  public Map<String,String> getVariables() {
    return variablesUsed;
  }
  public Map<String,String> getUserCommands() {return functionsUsed;}
  public String getCommand() {
    return input;
  }

  public void setCommand(String command) {
    input = command;
    commandGrouping.setCommand(command);
  }

  public void setLanguage(String lang) {
    language = lang;
    commandGrouping.setLanguage(lang);
  }

  public void parseCommand(){
    commandGrouping.parseCommand();
    variablesUsed.putAll(commandGrouping.setVariables());
    functionsUsed.putAll(commandGrouping.setFunctions());
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
 //   System.out.println("update" + col + " " + row + " " + angle);
  }

  public int getTurtleDistance() {
    return myMover.getDistanceSoFar();
  }

  public void turtleHome(boolean clearScreen) {
    if (clearScreen) {
      myMover.clearScreen();
    } else {
      myMover.resetTurtle();
    }
  }

  public double getTurtleRelativeXPos() {
    return turtleCol - myMover.getMoverCenterXPos();
  }

  public double getTurtleRelativeYPos() {
    return myMover.getMoverCenterYPos() - turtleRow;
  }

  public void setShape(int choice){
    view.setImageIndex(choice);
  }

  public int getShape(){
    return view.getCurrentImageIndex();
  }
}

