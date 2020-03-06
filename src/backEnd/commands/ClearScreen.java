package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ClearScreen extends Command {
  private final int NUMARGS = 0;
  private int distance;

  public ClearScreen(){
    super();
    super.numberOfArgs = NUMARGS;
  }

  public ClearScreen(LinkedList<String> varargs, Control control){
    super(varargs, control);
    control.turtleHome(true);
    distance = control.getTurtleDistance();
  }

  @Override
  public String commandValueReturn() {
    String ret = Integer.toString(distance);
    return ret;
  }
  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
