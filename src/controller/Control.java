package controller;

import frontEnd.Moveable;
import frontEnd.UserInterface;
import java.util.Map;
import java.util.TreeMap;

public class Control {

  private Moveable myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private final CommandSetAndExecute commandSetAndExecute;
  private final UserInterface view;
  private final Map<String,String> variablesUsed = new TreeMap<>();
  private final Map<String,String> functionsUsed = new TreeMap<>();
  private String commandReturnValue;
  private String finalReturnValue;

  /*
  Initializes controller
   */
  public Control(UserInterface UI) {
    view = UI;
    commandSetAndExecute = new CommandSetAndExecute(this);
  }

  public Map<String,String> getVariables() {
    return variablesUsed;
  }

  public Map<String,String> getUserCommands() {return functionsUsed;}

  public String getCommandReturnValue(){return commandReturnValue;}

  public String getFinalReturnValue(){return finalReturnValue;}

  public void setCommand(String command) {
    commandSetAndExecute.setCommandInput(command);
  }

  public void setLanguage(String lang) {
    commandSetAndExecute.setLanguage(lang);
  }

  public void parseCommand(){
    commandSetAndExecute.setFinalReturnValue(null);
    commandSetAndExecute.parseCommand();
    if(commandSetAndExecute.getVariables()!=null)variablesUsed.putAll(commandSetAndExecute.getVariables());
    if(commandSetAndExecute.getFunctions()!=null)functionsUsed.putAll(commandSetAndExecute.getFunctions());
    commandReturnValue = commandSetAndExecute.getCommandReturn();
    finalReturnValue = commandSetAndExecute.getFinalReturnValue();
    System.out.println("FINAL RETURN VALUE ------>" + finalReturnValue);
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

  public void setPenColor(int index) {
    myMover.setCurrentPenColorIndex(index);
  }

  public int getPenColor() {
    return myMover.getCurrentPenColorIndex();
  }

}

