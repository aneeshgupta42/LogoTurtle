package backEnd.commands;

import Controller.Control;

public class Right extends Command {
  public Right(String[] varargs, Control myControl)
  {
    super(varargs);
    double angle = Double.parseDouble(varargs[0]);
    myControl.updateTurtle(0,0, angle);
  }

}
