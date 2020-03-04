package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import frontEnd.Mover;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Control {


  private Mover myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private CommandGrouping commandGrouping;
  private String language;
  private String input;
  private Map<String,String> variablesUsed = new TreeMap<>();
  private StoreLists lists;



  /*
  Initializing a control (for reference storeLists is where all the data in lists is being passed)
   */
  public Control() {
    commandGrouping = new CommandGrouping();
    commandGrouping.setControl(this);
  }


  public Map getVariables() {
    return variablesUsed;
  }
  public Map getUserCommands() {return new TreeMap();}

  public void setVariables(Map saved) {
    variablesUsed = saved;
  }

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

