package Controller;

import frontEnd.Mover;
import java.util.Map;
import java.util.TreeMap;

public class Control {

  private Mover myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private CommandExecution commandGrouping;
  private String input;
  private String language;
  private Map<String,String> variablesUsed = new TreeMap<>();
  private Map<String,String> functionsUsed = new TreeMap<>();


  public Control() {
    commandGrouping = new CommandExecution(this);
    commandGrouping.setLanguage(language);
    commandGrouping.setCommand(input);
  }

  public Map<String,String> getVariables() {
    return variablesUsed;
  }

  public Map<String,String> getUserCommands() {return functionsUsed;}

  public String getCommand() { return input; }

  public String getLanguage(){ return language; }

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

  public void passTurtle(Mover mover) {
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
      myMover.resetTurtle();
    }
  }

  public Mover getTurtle() {
    return myMover;
  }

  public double getTurtleRelativeXPos() {
    return turtleCol - myMover.getMoverCenterXPos();
  }

  public double getTurtleRelativeYPos() {
    return myMover.getMoverCenterYPos() - turtleRow;
  }
}

