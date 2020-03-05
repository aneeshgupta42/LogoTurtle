package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
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
  protected Boolean repeat;
  private int b;

  public Command(){
  }

  public Command(LinkedList<String> varargs, Control control) {
    myControl = control;
  //  System.out.println("HIT COMMAND CONSTRUCT");
  }

  public void setControl(Control control){
      myControl = control;
     // System.out.println("Got Control: command");
  }

  public Control getMyControl() {
    return myControl;
  }

  public double getTurtleCurrentX() {
    return myControl.getTurtleCol();
  }

  public double getTurtleCurrentY() {
    return myControl.getTurtleRow();
  }

  public int getPenCurrentX() {
    return penXVal;
  }

  public int getPenCurrentY() {
    return penYVal;
  }

  public double getTurtleAngle() {
    return myControl.getTurtleAngle();
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
  public int repeatCom(){return b;}
  public boolean runnable(){return repeat;}



}
