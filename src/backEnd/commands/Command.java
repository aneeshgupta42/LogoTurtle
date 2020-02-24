package backEnd.commands;

import Controller.Control;
import java.util.Collection;
import java.util.Map;

public abstract class Command {

  private int turtleXVal;
  private int turtleYVal;
  private int penXVal;
  private int penYVal;
  private int turtleAngle;
  private int penAngle;
  private int penColor;
  private int turtleColor;
  private String language;
  private String myCommand;
  private String commandReturn;
  private Map c;
  protected int numberOfArgs;
  private Control myControl;


  public Command(String[] varargs, Control control) {
    myControl = control;
    System.out.println("HIT COMMAND CONSTRUCT");
  }

  public Command(String [] varargs){

  }

  public void setControl(Control control){
      myControl = control;
      System.out.println("Got Control: command");
  }

  public Control getMyControl() {
    return myControl;
  }

  public int getTurtleCurrentX() {
    return turtleXVal;
  }

  public int getTurtleCurrentY() {
    return turtleYVal;
  }

  public int getPenCurrentX() {
    return penXVal;
  }

  public int getPenCurrentY() {
    return penYVal;
  }

  public int getTurtleAngle() {
    return turtleAngle;
  }

  public int getPenAngle() {
    return penAngle;
  }

  public int getPenColor() {
    return penColor;
  }

  public int getTurtleColor() {
    return turtleColor;
  }

  public String getLanguage() {
    return language;
  }
  public String commandValueReturn(){
    return commandReturn;
  }
  public Map getVariablesCreated(){return c;}
  public int getNumberOfArgs(){return numberOfArgs;}



}
