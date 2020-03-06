package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class Right extends Command {
  private static final int NUMARGS = 1;
  private double angle;

  public Right(){
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Right(List<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs= NUMARGS;
    angle = Double.parseDouble(varargs.get(0));
    control.updateTurtle(0,0, angle, 0);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(angle);
  }
}
