package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Left extends Command {
  private static final int NUMARGS = 1;

  public Left(){
    super();
    super.numberOfArgs= NUMARGS;
  }
  public Left(LinkedList<String> varargs, Control myControl)
  {
    super(varargs, myControl);
    super.numberOfArgs= NUMARGS;
    double angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, -angle);
  }
}
