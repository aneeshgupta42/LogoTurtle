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
  private CommandExecution commandExecution;
  private UserInterface view;
  private Map<String,String> variablesUsed = new TreeMap<>();
  private Map<String,String> functionsUsed = new TreeMap<>();


  public Control(UserInterface UI) {
    view = UI;
    commandExecution = new CommandExecution(this);
  }

  public Map<String,String> getVariables() {
    return variablesUsed;
  }

  public Map<String,String> getUserCommands() {return functionsUsed;}

  public void setCommand(String command) {
    commandExecution.setCommand(command);
  }

  public void setLanguage(String lang) {
    commandExecution.setLanguage(lang);
  }

  public void parseCommand(){
    commandExecution.parseCommand();
    variablesUsed.putAll(commandExecution.setVariables());
    functionsUsed.putAll(commandExecution.setFunctions());
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
}

