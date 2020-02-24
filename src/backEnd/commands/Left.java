package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;

public class Left extends Command {

  public Left(String[] varargs, Control myControl)
  {
    super(varargs);
    double angle = Double.parseDouble(varargs[0]);
    myControl.updateTurtle(0,0, -angle);
  }
}
