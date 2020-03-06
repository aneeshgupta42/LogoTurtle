package backEnd.commands;

import controller.Control;
import java.util.List;

public class Left extends Command {
  private static final int NUMARGS = 1;
  private double angle;
  public Left(){
    super();
    super.numberOfArgs= NUMARGS;
  }
  public Left(List<String> varargs, Control myControl)
  {
    super(varargs, myControl);
    super.numberOfArgs= NUMARGS;
    angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, -angle, 0);
  }

  @Override
  public String commandValueReturn(){
    return Double.toString(angle);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
