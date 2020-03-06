package backEnd.commands;

import controller.Control;
import java.util.List;

public class ClearScreen extends Command {
  private final int NUMARGS = 0;
  private int distance;

  public ClearScreen(){
    super();
    super.numberOfArgs = NUMARGS;
  }

  public ClearScreen(List<String> varargs, Control control){
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
